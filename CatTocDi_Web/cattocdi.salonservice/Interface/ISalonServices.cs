using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace cattocdi.salonservice.Interface
{
    public interface ISalonServices
    {
        void RegisterSalonAccount(SalonViewModel newSalon);        
        SalonProfileViewModel GetSalonProfile(string salonId);
        List<WorkDayViewModel> GetSalonTimeTable(int salonId);
        void UpdateProfile(SalonProfileViewModel model);
    }
}
