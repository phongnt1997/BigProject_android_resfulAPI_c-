using cattocdi.salonservice.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.salonservice.Interface
{
    public interface IAppointmentServices
    {
        AppointmentSeprationViewModel getAllAppoitment(string AccountId);

        List<AppointmentViewmodel> getBydate(DateTime date, string accountId);

        bool cancelAppoitment(int appointmentId);
        bool approveAppointment(int appointmentId);
    }
}
