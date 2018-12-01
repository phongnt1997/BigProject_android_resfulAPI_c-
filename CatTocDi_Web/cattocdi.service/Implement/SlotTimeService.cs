using cattocdi.entity;
using cattocdi.repository;
using cattocdi.Service.Interface;
using cattocdi.Service.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.Implement
{
    public class SlotTimeService : ISlotTimeService
    {
        private IRepository<SlotTime> _slotRepo;
        private IRepository<Salon> _salonRepo;
        public SlotTimeService(IRepository<SlotTime> slotRepo, IRepository<Salon> salonRepo)
        {
            _slotRepo = slotRepo;
            _salonRepo = salonRepo;
        }        
        public List<SlotDateViewModel> SearchTimeSlot(int salonId, int duration)
        {
            var salon = _salonRepo.GetByID(salonId);
            var slotDateList = new List<SlotDateViewModel>();

            if (salon != null)
            {
                int salonCapacity = salon.Capacity ?? 0;
                int numberOfSlot = (int)Math.Ceiling((double)duration / 15); // Require Slots for appointment
                var date = DateTime.Now;
                var endDate = date.AddDays(7).Date;
                var currTime = date.TimeOfDay;
                int currSlot = (int)Math.Ceiling(currTime.TotalMinutes / 15);
                var timeMask = TimeSpan.FromMinutes(currSlot * 15);

                var salonSlots = _slotRepo.Gets()
                        .Where(s => s.SlotDate >= date.Date && s.SlotDate <= endDate)
                        .AsQueryable()
                        .ToList();
                salonSlots = salonSlots.Where(s => s.SalonId == salonId).ToList();

                foreach (var slotdate in salonSlots)
                {
                    var convertedSlots = ParseToSlotList(slotdate);                    
                    var availableSlots = GetAvailableSlot(convertedSlots, numberOfSlot, salonCapacity);
                    availableSlots = availableSlots
                                .Where(s => s.SlotDate > date.Date || (s.SlotDate == date.Date && s.Time >= date.TimeOfDay))
                                .ToList();

                    slotDateList.Add(new SlotDateViewModel
                    {
                        date = slotdate.SlotDate,
                        slots = availableSlots
                    });
                }
              
            }           
            return slotDateList;
        }
        private List<SlotTimeViewModel> GetAvailableSlot(List<SlotTimeViewModel> list, int totalSlot, int salonCapacity)
        {
            var availables = new List<SlotTimeViewModel>();
            for (int i = 0; i <= list.Count - totalSlot; i++)
            {
                
                if (list[i].Capacity != -1 && list[i + 1].Capacity != -1 && list[i + 2].Capacity != -1 &&
                    list[i].Capacity < salonCapacity && 
                    list[i + 1].Capacity < salonCapacity && 
                    list[i + 2].Capacity < salonCapacity)
                {
                    availables.Add(list[i]);
                }
            }
            return availables;
        }
        private List<SlotTimeViewModel> ParseToSlotList(SlotTime slotdate)
        {
            var list = new List<SlotTimeViewModel>(); 

            for (int i = 1; i <= 96; i++)
            {                
                var converted = slotdate.GetType().GetProperty($"Slot{i}").GetValue(slotdate);
                int capacity = -1;
                if (converted != null)
                {
                    capacity = Convert.ToInt32(converted);                  
                }
                list.Add(new SlotTimeViewModel
                {
                    Id = slotdate.Id,
                    SalonId = slotdate.SalonId,
                    SlotDate = slotdate.SlotDate,
                    Time = TimeSpan.FromMinutes(i * 15),
                    Capacity = capacity,
                    Index = i
                });
            }
            return list;
        }

    }
}
