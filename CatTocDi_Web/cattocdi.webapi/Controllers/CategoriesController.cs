using cattocdi.salonservice.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace cattocdi.webapi.Controllers
{
    [Authorize(Roles = "Salon")]
    [RoutePrefix("api/Categories")]
    public class CategoriesController : ApiController
    {
        private IServiceSalonService _serviceSalonService; 
        public CategoriesController(IServiceSalonService serviceSalonService)
        {
            _serviceSalonService = serviceSalonService;
        }
        
        public IHttpActionResult Get()
        {
            var salons = _serviceSalonService.GetCategories();
            return Json(salons);
        }

    }
}
