using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.ViewModel
{
    public class CustomerDetailViewModel
    {
        public int CustomerId { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public string Phone { get; set; }
        public bool Gender { get; set; }
        public string Email { get; set; }
        public List<AppointmentViewmodel> Appointments{get; set;}

    }
}
