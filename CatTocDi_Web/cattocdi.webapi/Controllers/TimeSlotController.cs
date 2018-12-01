using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace cattocdi.webapi.Controllers
{
    [Authorize(Roles = "Salon")]
    [RoutePrefix("api/TimeSlot")]
    public class TimeSlotController : ApiController
    {
        //[HttpPost]
        //[Route("WorkingHour")]
        //public IHttpActionResult UpdateWorkingHour()
        //{

        //}
    }
}
