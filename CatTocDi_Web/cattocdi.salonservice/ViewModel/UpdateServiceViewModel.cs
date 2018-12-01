using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class UpdateServiceViewModel
    {        
        public string AccountId { get; set; }
        public int ServiceId { get; set; }
        public double Price { get; set; }
        public int Duration { get; set; }

    }
}
