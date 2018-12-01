using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
   public class AppointmentViewModel
    {
        public int AppointmentId { get; set; }
        
        public int Status { get; set; }
        public DateTime BookDate { get; set; }
        public int Duration { get; set; }
        public TimeSpan TimeSlot { get; set; }
        public double DiscountPercent { get; set; }
        public int SalonID { get; set; }
        public DateTime StartTime { get; set; }
        public List<SalonServiceViewModel> Services { get; set; }
        public ReviewViewModel Reviews { get; set; }

    }
}
