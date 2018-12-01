using cattocdi.salonservice.Interface;
using cattocdi.salonservice.ViewModel;
using Elmah;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Results;

namespace cattocdi.webapi.Controllers
{
    [Authorize(Roles = "Salon")]
    [RoutePrefix("api/Salons")]
    public class SalonsController : ApiController
    {
        private ISalonServices _salonService;
        private IWorkingHourService _workingHourService;
        private ITimeSlotService _slotService;
        public SalonsController(ISalonServices salonService, IWorkingHourService workingHourService,
            ITimeSlotService slotService)
        {
            _salonService = salonService;
            _workingHourService = workingHourService;
            _slotService = slotService;
        }
        public IHttpActionResult Get()
        {
            var identity = (ClaimsIdentity)User.Identity;
            string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
            var profile = _salonService.GetSalonProfile(accountId);            
            return Json(profile);
        }    

        [HttpPost]
        [Route("Profile")]
        public IHttpActionResult Profile(SalonProfileViewModel model)
        {
            try
            {
                var identity = (ClaimsIdentity)User.Identity;
                string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
                model.AccountId = accountId;
                _salonService.UpdateProfile(model); 

            } catch (Exception ex)
            {
                ErrorSignal.FromCurrentContext().Raise(ex);
                return BadRequest("Update Profile Failed");
            }
            return Ok("Update Profile Success");
        }
        [HttpPost] 
        [Route("WorkingHour")]
        public IHttpActionResult WorkingHour(List<WorkingHourViewModel> workingHours)
        {
            try
            {
                var identity = (ClaimsIdentity)User.Identity;
                string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
                _workingHourService.Update(accountId, workingHours);
                _slotService.GenerateSlotForSalon(accountId);                
            }
            catch(Exception ex)
            {
                ErrorSignal.FromCurrentContext().Raise(ex);
                return BadRequest("Update Profile Failed");
            }
                        
            return Json("Update Working Hour Success");
        }       
    }
}
