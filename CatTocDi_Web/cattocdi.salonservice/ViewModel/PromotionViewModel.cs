using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class PromotionViewModel
    {
        public int Id { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public double DiscountPercent { get; set; }
        public string Description { get; set; }
        public int SalonId { get; set; }
        public string AccountId { get; set; }
        public byte Status { get; set; }
    }
}
