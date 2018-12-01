using cattocdi.salonservice.Implement;
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
    [RoutePrefix("api/Appointment")]
    public class AppointmentController : ApiController
    {
        AppointmentServices _ApmServices;
        public AppointmentController(AppointmentServices ApmServices)
        {
            _ApmServices = ApmServices;
        }
        // GET: api/Appointment
        public IHttpActionResult Get()
        {
            var identity = (ClaimsIdentity)User.Identity;
            string accountId = identity.Claims.FirstOrDefault(p => p.Type.Equals("AccountId")).Value;
            var result = _ApmServices.getAllAppoitment(accountId);
            return Json(result);
        }

        public IHttpActionResult GetByDate(DateTime date)
        {
            var identity = (ClaimsIdentity)User.Identity;
            string accountId = identity.Claims.FirstOrDefault(p => p.Type.Equals("AccountId")).Value;
            var result  = _ApmServices.getBydate(date, accountId);
            return Json(result);
        }

       
    }
}
