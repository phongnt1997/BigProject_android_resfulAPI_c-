using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Interface
{
    public interface IWorkingHourService
    {
        void Update(string accountId, List<WorkingHourViewModel> model);
    }
}
