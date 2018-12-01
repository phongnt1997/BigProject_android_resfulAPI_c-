using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
   public class TimeSlotViewModel
    {
        public int TimeSlotId { get; set; }
        public int SalonId { get; set; }
        public DateTime SlotDate { get; set; }
        public TimeSpan SlotTime { get; set; }
        public int DayOfWeek { get; set; }
        public bool SlotType { get; set; }
        public byte Status { get; set; }

    }
}
