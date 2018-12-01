using cattocdi.Service.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using cattocdi.Service.ViewModel.User;
using cattocdi.repository;
using cattocdi.entity;
using cattocdi.Service.Constant;

namespace cattocdi.Service.Implement
{
    public class AppointmentServices : IAppointmentServices
    {
        private IRepository<Customer> _customerRepo;
        private IRepository<Appointment> _apmRepo;
        private IRepository<Review> _reviewRepo;
        private IRepository<SalonService> _salonServiceRepo;
        private IRepository<SlotTime> _slotTimeRepo;
        private IUnitOfWork _unitOfWork;
        private IRepository<Promotion> _promotionRepo;

        public AppointmentServices(IUnitOfWork unitOfWork, 
                                IRepository<Review> reviewRepo, 
                                IRepository<Customer> customerRepo, 
                                IRepository<Appointment> apmRepo, 
                                IRepository<Promotion> promotionRepo,
                                IRepository<SlotTime> slotTimeRepo,
                                IRepository<SalonService> salonServiceRepo)
        {
            _promotionRepo = promotionRepo;
            _unitOfWork = unitOfWork;
            _customerRepo = customerRepo;
            _apmRepo = apmRepo;
            _salonServiceRepo = salonServiceRepo;
            _reviewRepo = reviewRepo;
            _slotTimeRepo = slotTimeRepo;
        }
        public void AddReview(ReviewViewModel model)
        {

            var newReview = new Review
            {
                AppointmentId = model.AppointmentId,
                Comment = model.Comment,
                Date = DateTime.Now,
                RateNumber = (byte)model.RateNumber,
            };
            _reviewRepo.Insert(newReview);
            _unitOfWork.SaveChanges();
        }

        public bool CancelAppointment(int appointmentId)
        {
            var apm = _apmRepo.Gets().Where(p => p.Id == appointmentId).FirstOrDefault();
            apm.Status = AppointmentStatusConstant.CANCEL;
            _apmRepo.Edit(apm);
            return _unitOfWork.SaveChanges() > 0;            
        }

        public List<AppointmentViewModel> GetAllAppointment(string accountId)
        {
            var customer = _customerRepo.Gets().Where(c => c.AccountId == accountId).FirstOrDefault();
            if (customer != null)
            {
                var apms = _apmRepo.Gets().Where(a => a.CustomerId == customer.CustomerId)
                    .OrderBy(t => t.Status)
                    .ThenByDescending(t => t.StartTime)
                    .Select(p => new AppointmentViewModel {
                        AppointmentId = p.Id,
                        BookDate = p.BookedDate,
                        DiscountPercent = _promotionRepo.Gets().Where(n => n.Id == p.PromotionId).Select(k => k.DiscountPercent).FirstOrDefault(),
                        Duration = p.Duration,
                        Status = p.Status,
                        Reviews = p.Reviews.Select(v => new ReviewViewModel
                        {
                            AppointmentId = v.AppointmentId,
                            Comment = v.Comment,
                            Date = v.Date,
                            RateNumber = v.RateNumber,
                            ReviewId = v.Id
                        }).FirstOrDefault(),
                        StartTime = p.StartTime,
                        SalonID = p.ServiceAppointments.Select(m => m.SalonService).Select(q => q.SalonId).FirstOrDefault(),
                        Services = p.ServiceAppointments.Select(m => m.SalonService).Select(q => new SalonServiceViewModel
                        {
                            AverageTime = q.AvarageTime ?? 0,
                            Price = q.Price ?? 0,
                            ServiceId = q.ServiceId,
                            ServiceName = q.Service.Name                           
                        }).ToList()
                    }).ToList();               
                return apms;
            }
            return null;
        }

        public void BookAppoint(NewAppointmentViewModel model)
        {
            var customerId = _customerRepo.Gets().Where(p => p.AccountId == model.AccountId).Select(x => x.CustomerId).FirstOrDefault();
            var startTime = DateTime.Parse(model.StartTime);
            model.CustomerId = customerId;
            var bookedServices = _salonServiceRepo.Gets()
                    .Where(s => model.Services.Contains(s.Id))
                    .Select(x => new ServiceAppointment
                    {
                        Price = x.Price ?? 0,
                        ServiceId = x.Id,
                    }).ToList();        
            
            var apm = new Appointment
            {
                BookedDate = DateTime.Now,
                CustomerId = model.CustomerId,
                PromotionId = model.PromotionId,
                Duration = model.Duration,
                Status = AppointmentStatusConstant.NOTAPPROVE,                
                StartTime = startTime,
                ServiceAppointments = bookedServices
            };
            // UPDATE SLOTS TO SLOT TIME 
            var slot = _slotTimeRepo.GetByID(model.SlotId);
            foreach (var index in model.Indexes)
            {
                var capacity = slot.GetType().GetProperty($"Slot{index}").GetValue(slot);
                byte cap = Convert.ToByte(capacity);
                cap += 1;
                slot.GetType().GetProperty($"Slot{index}").SetValue(slot, cap);
            }
            _slotTimeRepo.Edit(slot);                        
            _apmRepo.Insert(apm);
            _unitOfWork.SaveChanges();            
        }
    }
}
