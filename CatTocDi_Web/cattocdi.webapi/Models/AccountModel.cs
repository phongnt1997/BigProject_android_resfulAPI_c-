using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace cattocdi.webapi.Models
{
    public class AccountModel
    {
        public string UserName { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }       
        public string PhoneNumber { get; set; }
        public string LoggedOn { get; set; }
        public string Role { get; set; }
    }
    public class UserAccountModel : AccountModel
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public bool Gender { get; set; }
    }

    public class SalonAccountModel : AccountModel
    {
        public string SalonName { get; set; }
        public string Address { get; set; }
        public bool IsForMen { get; set; }
        public bool IsForWomen { get; set; }
    }

}