using cattocdi.Service.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using cattocdi.Service.ViewModel.User;
using cattocdi.repository;
using cattocdi.entity;

namespace cattocdi.Service.Implement
{
   public class CategoryServices : ICategoryServices
    {
        private IRepository<ServiceCategory> _categoryRepo;
        public CategoryServices(IRepository<ServiceCategory> categoryRepo)
        {
            _categoryRepo = categoryRepo;
        }
        public IEnumerable<CategoryViewModel> getAllCategory()
        {
            var cateGories = _categoryRepo.Gets().Select(s => new CategoryViewModel
            {
                CategoryName = s.Name,
                CategoryId = s.Id,
                Services = s.Services.Select(p => new ServiceViewModel
                {
                    CategoryId = p.CategoryId,
                    ServiceName = p.Name,
                    ServiceId = p.Id
                }).ToList()
            }).ToList();

            return cateGories;
        }
    }
}
