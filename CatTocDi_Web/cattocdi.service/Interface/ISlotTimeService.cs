using cattocdi.Service.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cattocdi.Service.Interface
{
    public interface ISlotTimeService
    {
        List<SlotDateViewModel> SearchTimeSlot(int salonId, int duration);
    }
}
