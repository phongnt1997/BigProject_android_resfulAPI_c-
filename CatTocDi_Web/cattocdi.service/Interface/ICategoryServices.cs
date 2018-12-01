using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using cattocdi.Service.ViewModel.User;

namespace cattocdi.Service.Interface
{
   public interface ICategoryServices
    {
        IEnumerable<CategoryViewModel> getAllCategory();
    }
}
