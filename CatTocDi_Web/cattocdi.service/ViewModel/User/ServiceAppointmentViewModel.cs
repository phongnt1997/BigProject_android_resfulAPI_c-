using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
    public class ServiceAppointmentViewModel
    {
        public int AppointmentId { get; set; }
        public int SalonServiceID { get; set; }
        public double Price { get; set; }
    }
}
