using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
    public class SalonViewModel
    {
        public int SalonId { get; set; }
        public string SalonName { get; set; }
        public string Address { get; set; }
        public double longtitude { get; set; }
        public double lattitude { get; set; }
        public string AccountId { get; set; }
        public bool IsForMen { get; set; }
        public int ReviewCount { get; set; }
        public bool IsForWomen { get; set; }
        public double RatingAvarage { get; set; }
        public PromotionViewModel Promotion {get; set;}
        public List<SalonServiceViewModel> Services { get; set; }
        public List <ClosedDayViewModel> ClosedDays { get; set; }
    }
}
