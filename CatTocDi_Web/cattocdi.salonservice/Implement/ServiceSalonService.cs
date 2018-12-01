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
    public class ServiceSalonService : IServiceSalonService
    {
        private IRepository<ServiceCategory> _categoryRepo;
        private IRepository<Service> _serviceRepo;
        private IRepository<SalonService> _salonServiceRepo;
        private IRepository<Salon> _salonRepo;
        private IUnitOfWork _unitOfWork;
        public ServiceSalonService(IRepository<ServiceCategory> categoryRepo,
            IRepository<Service> serviceRepo,
            IRepository<SalonService> salonServiceRepo,
            IRepository<Salon> salonRepo,
            IUnitOfWork unitOfWork
            )
        {
            _categoryRepo = categoryRepo;
            _serviceRepo = serviceRepo;
            _salonServiceRepo = salonServiceRepo;
            _salonRepo = salonRepo;
            _unitOfWork = unitOfWork;
        }

        public void DeleteSalonService(int salonServiceId)
        {
            var salonService = _salonServiceRepo.GetByID(salonServiceId);
            salonService.Disabled = true;
            _salonServiceRepo.Edit(salonService);
            _unitOfWork.SaveChanges();
        }

        public List<CategoryViewModel> GetCategories()
        {
            var categories = _categoryRepo.Gets().Select(c => new CategoryViewModel
            {
                CategoryId = c.Id,
                CategoryName = c.Name,
                Services = c.Services.Select(s => new ServiceViewModel {
                    ServiceId = s.Id,
                    ServiceName = s.Name
                }).ToList()
            }).ToList();
            return categories;
        }

        public List<SalonServiceViewModel> GetSalonServices(string accountId)
        {
            var salon = _salonRepo.Gets().Where(s => s.AccountId == accountId).FirstOrDefault();
            if (salon != null)
            {
                var services = _salonServiceRepo.Gets()
                        .Where(s => s.SalonId == salon.Id && s.Disabled == false)
                        .Select(s => new SalonServiceViewModel {
                            Id = s.Id,
                            ServiceId = s.ServiceId,
                            Price = s.Price ?? 0,
                            AvarageTime = s.AvarageTime ?? 0,
                            ServiceName = s.Service.Name,
                            CategoryId = s.Service.CategoryId,
                            CategoryName = s.Service.ServiceCategory.Name
                        })
                        .ToList();
                return services;
            }
            return null;            
        }
        
        public void UpdateSalonService(UpdateServiceViewModel model)
        {
            var salonId = _salonRepo.Gets().Where(s => s.AccountId == model.AccountId).Select(s => s.Id).FirstOrDefault();

            var foundedService = _salonServiceRepo.Gets()
                    .Where(s => s.SalonId == salonId && s.ServiceId == model.ServiceId)
                    .FirstOrDefault();
            if (foundedService != null)
            {
                
                foundedService.Price = model.Price;
                foundedService.AvarageTime = model.Duration;
                _salonServiceRepo.Edit(foundedService);
                _unitOfWork.SaveChanges();
            }
            else
            {
                var salon = _salonRepo.GetByID(salonId);
                var service = _serviceRepo.GetByID(model.ServiceId);
                if (salon != null && service != null)
                {
                    SalonService newService = new SalonService
                    {
                        Salon = salon,
                        Service = service,
                        Price = model.Price,
                        AvarageTime = model.Duration,
                        Disabled = false
                    };
                    _salonServiceRepo.Insert(newService);
                    _unitOfWork.SaveChanges();
                }
                else
                {
                    throw new Exception("Salon Not Found");
                }
            }
        }


    }
}
