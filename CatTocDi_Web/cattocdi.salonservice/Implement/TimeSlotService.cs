using cattocdi.entity;
using cattocdi.repository;
using cattocdi.salonservice.Interface;
using cattocdi.salonservice.Ultility;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Implement
{
    public class TimeSlotService : ITimeSlotService
    {
        private IRepository<SlotTime> _slotRepo;
        private IRepository<Salon> _salonRepo;
        private IUnitOfWork _unitOfWork;
        public TimeSlotService(IRepository<SlotTime> slotRepo, IUnitOfWork unitOfWork, IRepository<Salon> salonRepo)
        {
            _unitOfWork = unitOfWork;
            _slotRepo = slotRepo;
            _salonRepo = salonRepo;
        }

        public void CreateTimeSlot(Salon salon)
        {           
            if (salon != null)
            {
                Console.WriteLine($"SALON: {salon.Id}");
                var workingHours = salon.WorkingHours.Select(w => w)
                    .OrderBy(w => w.DayOfWeek)
                    .ToList();
                if (workingHours.Count > 0)
                {
                    foreach (var workday in workingHours)
                    {                        
                        var start = workday.StartHour;
                        var end = workday.EndHour;                        
                        Console.WriteLine($"DAY OF WEEK: {workday.DayOfWeek} - START:{start} - END: {end}");                        
                        if (workday.IsClosed == false)
                        {
                            GenerateSlot(salon.Id, workday.DayOfWeek, start, end);
                        }                            
                    }
                }                
            }
        }

        public void GenerateSlot(int salonId,byte dayOfWeek, TimeSpan start, TimeSpan end)
        {
            if (start > end) return;
            // Get next month
            var nextMonth = DateTime.Now.Month; // UPDATE CURRENT MONTH
            var currYear = DateTime.Now.Year;            
            var startSlot = (int)start.TotalMinutes / 15;
            var endSlot = (int)end.TotalMinutes / 15;         
            var betweenMinutes = (end - start).TotalMinutes;            
            
            var dates = DateUtil.GetDates(currYear, nextMonth, dayOfWeek);
            foreach (var date in dates)
            {
                Console.WriteLine($"GENERATE DATE: {date.ToShortDateString()}");
                var existedSalon =_slotRepo.Gets().Where(s => s.SalonId == salonId && s.SlotDate == date).FirstOrDefault();
                if (existedSalon == null)
                {
                    SlotTime newSlot = new SlotTime
                    {
                        SalonId = salonId,
                        SlotDate = date
                    };
                    _slotRepo.Insert(newSlot);
                    _unitOfWork.SaveChanges();
                }
                else
                {
                    for (int i = 1; i <= 96; i++)
                    {
                        existedSalon.GetType().GetProperty($"Slot{i}").SetValue(existedSalon, null);
                        _slotRepo.Edit(existedSalon);                        
                    }
                    _unitOfWork.SaveChanges();
                }

                var sql = "UPDATE [SlotTime] SET ";                
                for (int i = startSlot; i <= endSlot; i++)
                {                   
                    Console.WriteLine($"SLOT: {i}");
                    var slotName = "Slot" + i;
                    sql += $"{slotName}=0,";                    
                }
                sql = sql.TrimEnd(',');
                sql = sql + $" WHERE SalonId=@salonId and SlotDate=@slotDate";
                object[] parameters = new object[] {
                    new SqlParameter("@salonId", salonId),
                    new SqlParameter("@slotDate", date)
                };
                int result = _unitOfWork.ExecuteSqlCommand(sql, parameters);
                        
                _unitOfWork.SaveChanges();
            }
        }

        public void GenerateSlotForSalon(string AccountId)
        {
            var salon = _salonRepo.Gets().Where(s => s.AccountId == AccountId).FirstOrDefault();
            CreateTimeSlot(salon);
        }

        public void ScheduleTimeSlot()
        {
            var salons = _salonRepo.Gets().Select(s => s).ToList();
            foreach (var salon in salons)
            {
                CreateTimeSlot(salon);
            }
        }
    }
}
