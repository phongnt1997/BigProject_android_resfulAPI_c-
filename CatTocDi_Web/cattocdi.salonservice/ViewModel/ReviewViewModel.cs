using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class ReviewViewModel
    {
        public int ReviewId { get; set; }
        public int AppointmentId { get; set; }
        public DateTime Date { get; set; }
        public int RateNumber { get; set; }
        public string Comment { get; set; }
        public string CustomerName { get; set; } 
    }
}
