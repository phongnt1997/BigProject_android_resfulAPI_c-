using cattocdi.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.taskscheduler.Services.Interface
{
    public interface ITimeSlotService
    {
        void CreateTimeSlot(Salon salon);
        void ScheduleTimeSlot();
        void GenerateSlot(int salonId, byte dayOfWeek, TimeSpan start, TimeSpan end);
    }
}
