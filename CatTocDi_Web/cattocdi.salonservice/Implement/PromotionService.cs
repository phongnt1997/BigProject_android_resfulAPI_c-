using cattocdi.entity;
using cattocdi.repository;
using cattocdi.salonservice.Constant;
using cattocdi.salonservice.Interface;
using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Implement
{
    public class PromotionService : IPromotionService
    {
        private IRepository<Salon> _salonRepo;
        private IRepository<Promotion> _promotionRepo;
        private IRepository<Appointment> _apmRepo;
        private IUnitOfWork _unitOfWork;
        public PromotionService(IRepository<Salon> salonRepo, IRepository<Promotion> promotionRepo, IUnitOfWork unitOfWork, IRepository<Appointment> apmRepo)
        {
            _salonRepo = salonRepo;
            _promotionRepo = promotionRepo;
            _unitOfWork = unitOfWork;
            _apmRepo = apmRepo;
        }

        public void CreatePromotion(PromotionViewModel model)
        {
            var salonId = _salonRepo.Gets()
                .Where(s => s.AccountId == model.AccountId)
                .Select(s => s.Id).FirstOrDefault();

            var newPromotion = new Promotion
            {
                SalonId = salonId,
                PostDate = DateTime.Now,
                StartTime = model.StartTime,
                EndTime = model.EndTime,
                Description = model.Description,
                DiscountPercent = model.DiscountPercent,
                Status = (byte)PromotionEnum.NORMAL                
            };
            _promotionRepo.Insert(newPromotion);
            int result = _unitOfWork.SaveChanges();            
        }

        public List<PromotionViewModel> GetPromotions(string accountId)
        {
            var salonId = _salonRepo.Gets().Where(s => s.AccountId == accountId).Select(s => s.Id).FirstOrDefault();
            if (salonId > 0)
            {
                var list = _promotionRepo.Gets().Where(p => p.SalonId == salonId && p.EndTime > DateTime.Now)
                    .Select(s => new PromotionViewModel
                    {
                        Id = s.Id,
                        StartTime = s.StartTime,
                        EndTime = s.EndTime,
                        Status = s.Status?? 0,
                        Description = s.Description,
                        DiscountPercent = s.DiscountPercent
                    }).ToList();
                return list; 
            }
            return null; 
        }
        public bool CancelPromotion(int id)
        {
            var founded = _promotionRepo.GetByID(id);
            var usedPromotionIds = _apmRepo.Gets().Select(p => p.PromotionId).ToList();
            if(founded.StartTime <= DateTime.Now || usedPromotionIds.Contains(id)) {
                return false;
            }
            founded.Status = (int)PromotionEnum.CANCELED;
            _promotionRepo.Edit(founded);
            _unitOfWork.SaveChanges();
            return true;
        }
    }
}
