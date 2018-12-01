using cattocdi.Service.Interface;
using cattocdi.Service.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Web.Http;

namespace cattocdi.userapi.Controllers
{
    [RoutePrefix("api/Appointment")]
    [Authorize(Roles = "User")]
    public class AppointmentController : ApiController
    {
        private IAppointmentServices _apmServices;
        public AppointmentController(IAppointmentServices apmServices)
        {
            _apmServices = apmServices;
        }
        // GET: api/Appointment
        [HttpGet]
        public IHttpActionResult Get()
        {
            try
            {
                var identity = (ClaimsIdentity)User.Identity;
                string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
                var result =_apmServices.GetAllAppointment(accountId);
                return Json(result);
            }
            catch(Exception ex)
            {
                return BadRequest("Get Appointment FAiled");
            }
          
        }
        [HttpPost]
        [Route("Delete")]
        public IHttpActionResult CancelAppointment(int id)
        {
            var result = _apmServices.CancelAppointment(id);
            if(result)
            {
                return Ok("Canceled");
            }
            else
            {
                return BadRequest();
            }
        }
      

        [HttpPost]        
        public IHttpActionResult Book(NewAppointmentViewModel model)
        {
            try
            {
                var identity = (ClaimsIdentity)User.Identity;
                string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
                model.AccountId = accountId;
                _apmServices.BookAppoint(model);
                return Ok("Book Success");
            }
            catch(Exception ex)
            {
                return BadRequest("Book Failed");
            }            
        }

    }
}
