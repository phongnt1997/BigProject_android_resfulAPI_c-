using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.ViewModel.User
{
    public class CustomerViewModel
    {        
        public int CustomerId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public bool Gender { get; set; }
        public string AccountId { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }
    }
}
