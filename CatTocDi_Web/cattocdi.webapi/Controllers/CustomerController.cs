using cattocdi.salonservice.Implement;
using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Web.Http;

namespace cattocdi.webapi.Controllers
{
    [Authorize(Roles = "Salon")]
    [RoutePrefix("api/Customer")]
    public class CustomerController : ApiController
    {
        private CustomerService _cusRepo;
        public CustomerController(CustomerService cusRepo)
        {
            _cusRepo = cusRepo;
        } 
        public IHttpActionResult Get()
        {
            var identity = (ClaimsIdentity)User.Identity;
            string acountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
            var customers = _cusRepo.GetAllCustomer(acountId);
            return Json(customers);
        }

        public IHttpActionResult Get(int id)
        {
            var cus = _cusRepo.GetById(id);
            return Json(cus);
        }
    }
}
