using cattocdi.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Interface
{
    public interface ITimeSlotService
    {
        void GenerateSlotForSalon(string AccountId);
        void CreateTimeSlot(Salon salon);
        void ScheduleTimeSlot();
        void GenerateSlot(int salonId, byte dayOfWeek, TimeSpan start, TimeSpan end);
    }
}
