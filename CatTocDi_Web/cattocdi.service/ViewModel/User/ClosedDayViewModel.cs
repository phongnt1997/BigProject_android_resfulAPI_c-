using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
    public class ClosedDayViewModel
    {
        public int ClosedDayId { get; set; }
        public DateTime Date { get; set; }
        public string Description { get; set; }
        public int SalonId { get; set; }
    }
}
