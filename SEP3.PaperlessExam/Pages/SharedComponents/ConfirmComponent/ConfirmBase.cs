using System.Threading.Tasks;
using Microsoft.AspNetCore.Components;

namespace SEP3.PaperlessExam.Pages.SharedComponents.ConfirmComponent
{
    public class ConfirmBase : ComponentBase
    {
        [Parameter] public string ConfirmationTitle { get; set; } = "Delete Confirmation";
        [Parameter] public string ConfirmationMessage { get; set; } = "Are you sure you want to delete?";
        protected bool ShowConfirmation { get; set; }

        public void Show()
        {
            ShowConfirmation = true;
            StateHasChanged();
        }
        
        protected async Task OnConfirmationChange(bool value)
        {
            ShowConfirmation = false;
            await ConfirmationChanged.InvokeAsync(value);
        }

        [Parameter] public EventCallback<bool> ConfirmationChanged { get; set; }
    }
}