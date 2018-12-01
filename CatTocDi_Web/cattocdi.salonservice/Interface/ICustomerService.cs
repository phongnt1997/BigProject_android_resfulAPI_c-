using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Interface
{
    public interface ICustomerService
    {
        void CreateCustomerAccount(CustomerViewModel model);
        List<CustomerViewModel> GetAllCustomer(string AccountId);
        CustomerDetailViewModel GetById(int id);
    }
}
