using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class AppointmentViewmodel
    {
        public int AppointmentId { get; set; }
        public int CustomerId { get; set; }
        public int Status { get; set; }
        public DateTime BookedDate { get; set; }
        public int Duration { get; set; }
        public TimeSpan TimeSlot { get; set; }
        public DateTime StartTime { get; set; }
        public PromotionViewModel Promotion { get; set; }
        public List<SalonServiceViewModel> Services { get; set; }
        public CustomerViewModel Customer { get; set; }

    }
}
