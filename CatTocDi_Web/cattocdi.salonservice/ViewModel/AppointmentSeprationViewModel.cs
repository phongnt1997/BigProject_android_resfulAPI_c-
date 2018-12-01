using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class AppointmentSeprationViewModel
    {
        public int Capacity { get; set; }
        public List<AppointmentViewmodel> ListToday { get; set; }
        public List<AppointmentViewmodel> ListNotApprove { get; set; }

    }
}
