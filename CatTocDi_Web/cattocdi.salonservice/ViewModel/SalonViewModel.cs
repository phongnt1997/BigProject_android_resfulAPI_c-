using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class SalonViewModel
    {        
        public string SalonName { get; set; }
        public string Address { get; set; }    
        public string AccountId { get; set; }
        public bool IsForMen { get; set; }
        public bool IsForWomen { get; set; }
        public float RatingAvarage { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
    }
}
