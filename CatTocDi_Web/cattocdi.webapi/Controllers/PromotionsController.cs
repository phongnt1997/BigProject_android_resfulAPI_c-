using cattocdi.salonservice.Interface;
using cattocdi.salonservice.ViewModel;
using Elmah;
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
    [RoutePrefix("api/Promotions")]
    public class PromotionsController : ApiController
    {
        private IPromotionService _promotionService;
        public PromotionsController(IPromotionService promotionService)
        {
            _promotionService = promotionService;
        }

        [HttpGet]        
        public IHttpActionResult Get()
        {
            var identity = (ClaimsIdentity)User.Identity;
            string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
            var result = _promotionService.GetPromotions(accountId);
            return Json(result);            
        }
        [HttpPost]
        [Route("Delete")]
        public IHttpActionResult Cancel(int id)
        {
            bool result = _promotionService.CancelPromotion(id);
            if(result)
            {
                return Ok("Cancel Promotions Success");
            }
            return BadRequest("Update failed");

        }
        [HttpPost]        
        public IHttpActionResult Post(PromotionViewModel model)
        {
            try
            { 
                var identity = (ClaimsIdentity)User.Identity;
                string accountId = identity.Claims.FirstOrDefault(c => c.Type.Equals("AccountId")).Value;
                model.AccountId = accountId;
                _promotionService.CreatePromotion(model);
            }
            catch(Exception ex)
            {
                ErrorSignal.FromCurrentContext().Raise(ex);
                return BadRequest("Create Promotion Failed");
            }            
            return Ok("Create Promotion Sucess");
        }
    }
}
