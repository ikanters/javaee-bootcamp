/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function handleClose(dialog, args) {
    if (!args.validationFailed && !args.savedFailed) {
        dialog.hide();
    }
}
