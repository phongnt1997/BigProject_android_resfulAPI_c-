using cattocdi.Service.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace cattocdi.userapi.Controllers
{
    [RoutePrefix("api/Slot")]
    [Authorize(Roles = "User")]
    public class SlotController : ApiController
    {
        private ISlotTimeService _slotService;
        public SlotController(ISlotTimeService slotService)
        {
            _slotService = slotService;
        }
        [HttpGet]   
        [Route("Available")]
        public IHttpActionResult AvailableSlot(int salonId, int duration)
        {
            try
            {
                var slotdates = _slotService.SearchTimeSlot(salonId, duration);
                return Json(slotdates);
            }
            catch (Exception ex)
            {
                return BadRequest("Get Available Slot Failed");
            }            
        }
    }
}
