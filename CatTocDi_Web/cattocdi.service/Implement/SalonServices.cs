using cattocdi.entity;
using cattocdi.repository;
using cattocdi.Service.Constant;
using cattocdi.Service.Interface;
using cattocdi.Service.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace cattocdi.Service.Implement
{
    public class SalonServices : ISalonServices
    {
        private IRepository<Salon> _salonRepo;
        private IUnitOfWork _unitOfWork;
        private IRepository<cattocdi.entity.Service> _serviceRepo;
        private IRepository<ServiceCategory> _categoryRepo;
        private IRepository<ServiceAppointment> _serviceAptRepo;
        private IRepository<Review> _reviewRepo; 
 

        public SalonServices(IRepository<Salon> salonRepo, IUnitOfWork unitOfWork, 
            IRepository<cattocdi.entity.Service> serviceRepo, IRepository<ServiceCategory> categoryRepo,
            IRepository<ServiceAppointment> serviceAptRepo,
            IRepository<Review> reviewRepo)
        {
            _serviceRepo = serviceRepo;
            _salonRepo = salonRepo;
            _unitOfWork = unitOfWork;
            _categoryRepo = categoryRepo;
            _serviceAptRepo = serviceAptRepo;
            _reviewRepo = reviewRepo;
        }

        public IEnumerable<SalonViewModel> GetAllSalon()
        {
            var salons = _salonRepo.Gets().Select(s => new SalonViewModel
            {
                SalonId = s.Id,
                AccountId = s.AccountId,
                Address = s.Address,
                longtitude = s.Longitude ?? 0,
                lattitude = s.Latitude ?? 0,
                IsForMen = s.IsForMen ?? false,
                IsForWomen = s.IsForWomen ?? false,
                RatingAvarage = s.RatingAverage ?? 0,
                SalonName = s.Name,
                Promotion = s.Promotions.OrderBy(o => o.EndTime).Select(x => new PromotionViewModel {
                    Description = x.Description,
                    DiscountPercent = x.DiscountPercent,
                    EndTime = x.EndTime,
                    StartTime = x.StartTime,
                    PromotionId = x.Id,
                    SalonId = x.SalonId
                }).FirstOrDefault(),
            });

            foreach (var item in salons)
            {
                var salonId = item.SalonId;
                var aptIds = _serviceAptRepo.Gets().Where(s => s.SalonService.SalonId == salonId)
                                        .Select(p => p.AppointmentId)
                                        .Distinct()
                                        .ToList();
                var reviews = _reviewRepo.Gets().Where(r => aptIds.Contains(r.AppointmentId)).Count();
                item.ReviewCount = reviews;
            }
            return salons;
        }
        public void RegisterSalonAccount(SalonViewModel newSalon)
        {
            if (newSalon != null)
            {
                var salon = new Salon
                {
                    Name = newSalon.SalonName,
                    AccountId = newSalon.AccountId,
                    Address = newSalon.Address,
                    RatingAverage = 0,
                    IsForMen = newSalon.IsForMen,
                    IsForWomen = newSalon.IsForWomen
                };
                _salonRepo.Insert(salon);
                _unitOfWork.SaveChanges();
            }
        }
        public IEnumerable<SalonViewModel> SearchSalon(string nameAndAddress, string servicename)
        {
            var salons = _salonRepo.Gets().Select(s => new SalonViewModel
            {
                SalonId = s.Id,
                AccountId = s.AccountId,
                Address = s.Address,
                IsForMen = s.IsForMen ?? false,
                IsForWomen = s.IsForWomen ?? false,
                RatingAvarage = s.RatingAverage ?? 0,
                longtitude = s.Longitude ?? 0,
                lattitude = s.Latitude ?? 0,
                SalonName = s.Name,
                Promotion = s.Promotions.Where(v => v.EndTime > DateTime.Now).OrderBy(e => e.StartTime).Select(x => new PromotionViewModel
                {
                    Description = x.Description,
                    DiscountPercent = x.DiscountPercent,
                    EndTime = x.EndTime,
                    StartTime = x.StartTime,
                    PromotionId = x.Id,
                    SalonId = x.SalonId
                }).FirstOrDefault(),
                Services = s.SalonServices.Where(q => q.SalonId == s.Id && q.Disabled == false).ToList().Select(x => new SalonServiceViewModel
                {
                    SalonServiceId = x.Id,
                    AverageTime = x.AvarageTime ?? 0,
                    Price = x.Price ?? 0,
                    SalonId = x.SalonId,
                    ServiceId = x.ServiceId,
                    ServiceName = x.Service.Name,
                    CategoryName = x.Service.ServiceCategory.Name,
                    CategoryId = x.Service.ServiceCategory.Id,

                }).ToList(),

                ClosedDays = s.ClosedDays.Select(c => new ClosedDayViewModel {
                    ClosedDayId = c.Id,
                    Date = c.Date ?? DateTime.Now,
                    Description = c.Description,
                    SalonId = c.SalonId
                }).ToList()


            });
            foreach (var item in salons)
            {
                var salonId = item.SalonId;
                var aptIds = _serviceAptRepo.Gets().Where(s => s.SalonService.SalonId == salonId)
                                        .Select(p => p.AppointmentId)
                                        .Distinct()
                                        .ToList();
                var reviews = _reviewRepo.Gets().Where(r => aptIds.Contains(r.AppointmentId)).Count();
                item.ReviewCount = reviews;

            }


           
            IEnumerable<SalonViewModel> result = salons;
            if(nameAndAddress != null && nameAndAddress.Length > 0)
            {
                string[] listStr = Regex.Split(nameAndAddress.Trim(), " ");
                foreach (string s in listStr)
                {
                    result = result.Where(w => w.SalonName.ToUpper().Contains(s.Trim().ToUpper()) || (w.Address == null ? false : w.Address.ToUpper().Contains(s.Trim().ToUpper()))).ToList();
                }
            }
            
            if(servicename != null && servicename.Length > 0)
            {
                var listStr = Regex.Split(servicename, ",").ToList();
                listStr.RemoveAt(listStr.Count - 1);

                foreach (string s in listStr)
                {
                    result = result.Where(w => w.Services.Where(n => n.ServiceName.Contains(s.Trim())).ToList().Count > 0).ToList();
                }
            }

            return result;
        }

        public SalonDetailViewModel getSalonById(int id)
        {
            var aptIds = _serviceAptRepo.Gets().Where(s => s.SalonService.SalonId == id)
                                        .Select(p => p.AppointmentId)
                                        .Distinct()
                                        .ToList();
            var salon = _salonRepo.Gets().Where(p => p.Id == id).Select(m => new SalonDetailViewModel
            {
                SalonId = m.Id,
                AccountId = m.AccountId,
                Address = m.Address,
                IsForMen = m.IsForMen ?? false,
                IsForWomen = m.IsForWomen ?? false,
                lattitude = m.Latitude ?? 0,
                longtitude = m.Longitude ?? 0,
                WorkingHours = m.WorkingHours.Select(p => new WorkDayViewModel
                {
                    DayOfWeek = p.DayOfWeek,
                    FromHour = p.StartHour,
                    ToHour = p.EndHour,
                    IsClosed = p.IsClosed 
                }).ToList(),
                Promotions = m.Promotions.Where(x => x.EndTime > DateTime.Now && x.Status != (byte)PromotionEnum.CANCELED)
                    .OrderBy(e => e.StartTime)
                    .Select(x => new PromotionViewModel
                    {
                        Description = x.Description,
                        DiscountPercent = x.DiscountPercent,
                        EndTime = x.EndTime,
                        StartTime = x.StartTime,
                        PromotionId = x.Id,
                        SalonId = x.SalonId
                    }).ToList(),
                RatingAvarage = m.RatingAverage ?? 0,
                SalonName = m.Name,
                Services = m.SalonServices.Where(q => q.SalonId == m.Id).ToList().Select(x => new SalonServiceViewModel
                {
                    SalonServiceId = x.Id,
                    AverageTime = x.AvarageTime ?? 0,
                    Price = x.Price ?? 0,
                    SalonId = x.SalonId,
                    ServiceId = x.ServiceId,
                    ServiceName = x.Service.Name,
                    CategoryName = x.Service.ServiceCategory.Name,
                    CategoryId = x.Service.ServiceCategory.Id,

                }).ToList(),

                Reviews = _reviewRepo.Gets().Where(r => aptIds.Contains(r.AppointmentId))
                            .Select(x => new ReviewViewModel {
                                AppointmentId = x.AppointmentId,
                                Comment = x.Comment,
                                Date = x.Date,
                                RateNumber = x.RateNumber,
                                ReviewId = x.Id
                            }).ToList(),              
            }).FirstOrDefault();
            return salon;
        }
    }
}
