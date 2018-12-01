using System.Collections.Generic;
using System.Web.Http;
using cattocdi.Service.Interface;

namespace cattocdi.userapi.Controllers
{
    [RoutePrefix("api/Category")]
    [Authorize(Roles = "User")]
    public class CategoryController : ApiController
    {
        private ICategoryServices _categoryService;

        public CategoryController(ICategoryServices categoryService)
        {
            _categoryService = categoryService;
        }
        // GET: api/ServiceCategory
        [HttpGet]
        public IHttpActionResult Gets()
        {
            var category = _categoryService.getAllCategory();
            return Json(category);
        }

        // GET: api/ServiceCategory/5
    }
}
