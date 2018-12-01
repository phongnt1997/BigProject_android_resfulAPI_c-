using cattocdi.entity;
using cattocdi.repository;
using cattocdi.salonservice.Interface;
using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Implement
{
    public class ReviewServices : IReviewServices
    {
        IRepository<Review> _reviewRepo;
        IRepository<SalonService> _saserRepo;
        IRepository<Salon> _salonRepo;
        public ReviewServices(IRepository<Review> reviewRepo, IRepository<SalonService> saserRepo, IRepository<Salon> salonRepo)
        {
            _reviewRepo = reviewRepo;
            _saserRepo = saserRepo;
            _salonRepo = salonRepo;
        }
        public List<ReviewViewModel> GetAllReviews(string accountId)
        {
            var salonId = _salonRepo.Gets().Where(s => s.AccountId == accountId).Select(s => s.Id).FirstOrDefault();
            var appointmentIds = _saserRepo.Gets().Where(p => p.SalonId == salonId).Select(v => v.ServiceAppointments.Select(c => c.AppointmentId)).FirstOrDefault();
            var reviews = _reviewRepo.Gets().Where(p => appointmentIds.Contains(p.AppointmentId)).Select(v => new ReviewViewModel
            {
                AppointmentId = v.AppointmentId,
                Comment = v.Comment,
                Date = v.Date,
                RateNumber = v.RateNumber,
                ReviewId = v.Id,
                CustomerName = v.Appointment.Customer.FirstName
                
            }).ToList();
            return reviews;
        }
    }
}
