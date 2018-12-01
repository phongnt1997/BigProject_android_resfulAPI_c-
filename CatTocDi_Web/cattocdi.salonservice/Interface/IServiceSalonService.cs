using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Interface
{
    public interface IServiceSalonService
    {
        List<CategoryViewModel> GetCategories();
        void UpdateSalonService(UpdateServiceViewModel model);
        List<SalonServiceViewModel> GetSalonServices(string accountId);

        void DeleteSalonService(int salonServiceId);        
    }
}
