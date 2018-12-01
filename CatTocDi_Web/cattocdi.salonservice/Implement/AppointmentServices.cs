using cattocdi.salonservice.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using cattocdi.salonservice.ViewModel;
using cattocdi.repository;
using cattocdi.entity;
using cattocdi.salonservice.Constant;

namespace cattocdi.salonservice.Implement
{
    
    public class AppointmentServices : IAppointmentServices
    {
        IRepository<Appointment> _apmRepo;
        IRepository<Salon> _salonRepo;
        IUnitOfWork _unitOfWork;
        public AppointmentServices(IRepository<Appointment> apmRepo, IRepository<Salon> salonRepo, IUnitOfWork unitOfWork)
        {
            _apmRepo = apmRepo;
            _salonRepo = salonRepo;
            _unitOfWork = unitOfWork;
        }

        public bool approveAppointment(int appointmentId)
        {
            var apm = _apmRepo.Gets().Where(p => p.Id == appointmentId).FirstOrDefault(); ;
            if (apm != null)
            {
                if (apm.BookedDate < DateTime.Today)
                {
                    apm.Status = (int)AppoitmentStatusEnum.APPROVE;
                    _apmRepo.Edit(apm);
                    _apmRepo.Save();
                    return _unitOfWork.SaveChanges() > 0;
                }
            }

            return false;
        }

        public bool cancelAppoitment(int appoitmentId)
        {
            var apm = _apmRepo.Gets().Where(p => p.Id == appoitmentId).FirstOrDefault(); ;
            if(apm != null)
            {
                if(apm.BookedDate < DateTime.Today)
                {
                    apm.Status = (int)AppoitmentStatusEnum.CANCEL;
                    _apmRepo.Edit(apm);
                    _apmRepo.Save();
                   return _unitOfWork.SaveChanges() > 0;
                }
            }
            return false;            
        }

        public AppointmentSeprationViewModel getAllAppoitment(string AccountId)
        {
            var salon = _salonRepo.Gets().Where(p => p.AccountId.Equals(AccountId)).FirstOrDefault();
            List<int> salonServices = salon.SalonServices.Where(v => v.SalonId == salon.Id).Select(l => l.Id).ToList();
            var apms = _apmRepo.Gets();
            var result = new AppointmentSeprationViewModel {
                Capacity = salon.Capacity ?? 0,
                ListToday  = apms.Where(p => p.ServiceAppointments.Where(x => salonServices.Contains(x.ServiceId)).Count() > 0 && p.BookedDate == DateTime.Now  && p.Status != 2).Select(m => new AppointmentViewmodel
                {
                    AppointmentId = m.Id,
                    BookedDate = m.BookedDate,
                    CustomerId = m.CustomerId,
                    Duration = m.Duration,
                    Status = m.Status,    
                    StartTime = m.StartTime,
                    //TimeSlot = m.TimeSlot,
                    Customer = new CustomerViewModel
                    {
                        CustomerId = m.Customer.CustomerId,
                        Firstname = m.Customer.FirstName,
                        Lastname = m.Customer.LastName,
                        Gender = m.Customer.Gender ?? false,
                        Phone = m.Customer.Phone
                    },
                    Promotion = m.Promotion != null ? new PromotionViewModel
                    {
                        Description = m.Promotion.Description,
                        DiscountPercent = m.Promotion.DiscountPercent,
                        EndTime = m.Promotion.EndTime,
                        SalonId = m.Promotion.SalonId,
                        StartTime = m.Promotion.StartTime,
                        Id = m.Promotion.Id,
                        Status = m.Promotion.Status ?? 0,
                    } : null,
                    Services = m.ServiceAppointments.Select(p => p.SalonService).Select(q => new SalonServiceViewModel
                    {
                        AvarageTime = q.AvarageTime ?? 0,
                        Price = q.Price ?? 0,
                        ServiceId = q.ServiceId,
                        ServiceName = q.Service.Name
                    }).ToList()
                }).ToList(),

                ListNotApprove = apms.Where(p => p.ServiceAppointments.Where(x => salonServices.Contains(x.ServiceId)).Count() > 0 && p.Status == 0).Select(m => new AppointmentViewmodel
                {
                    AppointmentId = m.Id,
                    BookedDate = m.BookedDate,
                    CustomerId = m.CustomerId,
                    Duration = m.Duration,
                    Status = m.Status,
                    StartTime = m.StartTime,
                    //TimeSlot = m.TimeSlot,
                    Customer = new CustomerViewModel
                    {
                        CustomerId = m.Customer.CustomerId,
                        Firstname = m.Customer.FirstName,
                        Lastname = m.Customer.LastName,
                        Gender = m.Customer.Gender ?? false,
                        Phone = m.Customer.Phone                            
                    },
                    Services = m.ServiceAppointments.Select(p => p.SalonService).Select(q => new SalonServiceViewModel
                    {
                        AvarageTime = q.AvarageTime ?? 0,
                        Price = q.Price ?? 0,
                        ServiceId = q.ServiceId,
                        ServiceName = q.Service.Name

                    }).ToList(),
                    Promotion = m.Promotion != null ? new PromotionViewModel
                    {
                        Description = m.Promotion.Description,
                        DiscountPercent = m.Promotion.DiscountPercent,
                        EndTime = m.Promotion.EndTime,
                        SalonId = m.Promotion.SalonId,
                        StartTime = m.Promotion.StartTime,
                        Id = m.Promotion.Id,
                        Status = m.Promotion.Status ?? 0
                    } : null
                }).ToList(),
            };

            return result;
        }

        public List<AppointmentViewmodel> getBydate(DateTime date, string accountId)
        {
            var salon = _salonRepo.Gets().Where(p => p.AccountId.Equals(accountId)).FirstOrDefault();
            List<int> salonServices = salon.SalonServices.Where(v => v.SalonId == salon.Id).Select(l => l.Id).ToList();
            var apms = _apmRepo.Gets();
            var result = apms.Where(p => p.ServiceAppointments.Where(x => salonServices.Contains(x.ServiceId)).Count() > 0 && p.BookedDate == date && p.Status != 2).Select(m => new AppointmentViewmodel
            {
                AppointmentId = m.Id,
                BookedDate = m.BookedDate,
                CustomerId = m.CustomerId,
                Duration = m.Duration,
                Status = m.Status,
                //TimeSlot = m.TimeSlot,
                Customer = new CustomerViewModel
                {
                    CustomerId = m.Customer.CustomerId,
                    Firstname = m.Customer.FirstName,
                    Lastname = m.Customer.LastName,
                    Gender = m.Customer.Gender ?? false,
                    Phone = m.Customer.Phone

                },
                Promotion = m.Promotion != null ? new PromotionViewModel
                {
                    Description = m.Promotion.Description,
                    DiscountPercent = m.Promotion.DiscountPercent,
                    EndTime = m.Promotion.EndTime,
                    SalonId = m.Promotion.SalonId,
                    StartTime = m.Promotion.StartTime,
                    Id = m.Promotion.Id,
                    Status = m.Promotion.Status ?? 0,
                } : null,
                Services = m.ServiceAppointments.Select(p => p.SalonService).Select(q => new SalonServiceViewModel
                {
                    AvarageTime = q.AvarageTime ?? 0,
                    Price = q.Price ?? 0,
                    ServiceId = q.ServiceId,
                    ServiceName = q.Service.Name

                }).ToList()
            }).ToList();
            return result;
        }
    }
}
