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
    public class WorkingHourService : IWorkingHourService
    {
        private IRepository<WorkingHour> _workingHourRepo;
        private IRepository<Salon> _salonRepo;
        private IUnitOfWork _unitOfWork;
        public WorkingHourService(IRepository<WorkingHour> workingHourRepo, IRepository<Salon> salonRepo,
            IUnitOfWork unitOfWork)
        {
            _workingHourRepo = workingHourRepo;
            _salonRepo = salonRepo;
            _unitOfWork = unitOfWork;
        }
        public void Update(string AccountId, List<WorkingHourViewModel> workingHours)
        {
            var salon = _salonRepo.Gets().FirstOrDefault(s => s.AccountId == AccountId);
            if (salon != null)
            {
                var list = _workingHourRepo.Gets()
                    .Where(w => w.SalonId == salon.Id)
                    .Select(w => w)
                    .ToList();
                foreach (var item in workingHours)
                {
                    var filtered = list.Where(w => w.DayOfWeek == item.DayOfWeek).FirstOrDefault();
                    if (filtered != null)
                    {
                        // Update Working Hour
                        filtered.StartHour = TimeSpan.Parse(item.FromHour);
                        filtered.EndHour = TimeSpan.Parse(item.ToHour);
                        filtered.IsClosed = item.IsClosed;
                        _workingHourRepo.Edit(filtered);
                        _unitOfWork.SaveChanges();    
                    }
                    else
                    {
                        // Add Working Hour
                        var newWorkingHour = new WorkingHour {
                            SalonId = salon.Id,
                            DayOfWeek = (byte)item.DayOfWeek,
                            StartHour = TimeSpan.Parse(item.FromHour),
                            EndHour = TimeSpan.Parse(item.ToHour),
                            IsClosed = item.IsClosed
                        };
                        _workingHourRepo.Insert(newWorkingHour);
                        _unitOfWork.SaveChanges();
                    }
                }
            }
            else
            {
                throw new Exception("Cannot Found Salon");
            }            
        }
    }
}
