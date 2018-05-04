function validateForEmpty_editForm(event){
    let a = $(event.target);
    let input = a.parent().parent().find('td .editable');
    let inputText = input.val();
    let div = a.parent().parent().find('.editable_div');
    if (isEmpty(inputText)){
        input.addClass('error_field');
    } else {
        a.parent().removeClass('error_field');

        div.show();
        div[0].textContent = inputText;

        input.hide();

        a.removeClass('append_edit_button');
        a.addClass('edit_button');
        a[0].onclick = edit_editForm;
    }
}

function isEmpty(text){
    return text.length == 0;
}

function edit_editForm(event){
    let a = $(event.target);
    let input = a.parent().parent().find('td .editable');
    let div = a.parent().parent().find('.editable_div');
    let inputText = div[0].textContent;
    input.show();
    input.val(inputText);

    div.hide();

    a.removeClass('edit_button');
    a.addClass('append_edit_button');
     a[0].onclick = validateForEmpty_editForm;
}

function addBankRow(event){
let table = $('#edit_t');

let tr = document.createElement('tr');

let select = document.getElementsByClassName('bank_select')[0].cloneNode(true);
$(select).show();
let selectTd = document.createElement('td');
selectTd.appendChild(select);
tr.appendChild(selectTd);

let accountTd = document.createElement('td');
let accountDiv = document.createElement('div');
accountDiv.classList.add('editable_div');
accountTd.appendChild(accountDiv);

let accountInput = document.createElement('input');
accountInput.oninput = validateInt24;
accountInput.classList.add('editable');
accountInput.classList.add('bank_input');
accountInput.type = 'text';

accountTd.appendChild(accountDiv);
accountTd.appendChild(accountInput);
tr.appendChild(accountTd);

let appendEditButtonTd = document.createElement('td');
let appendEditButton = document.createElement('a');
appendEditButton.style = 'padding: 1px; cursor:pointer;';
appendEditButton.classList.add('append_edit_button');
appendEditButton.onclick = validateForEmpty_editForm;
appendEditButtonTd.appendChild(appendEditButton);

let removeButtonTd = document.createElement('td');
let removeButton = document.createElement('a');
removeButton.style = 'padding: 1px; cursor:pointer;';
removeButton.classList.add('remove_button');
removeButton.onclick = removeRow;
removeButtonTd.appendChild(removeButton);

tr.appendChild(appendEditButtonTd);
tr.appendChild(removeButtonTd);

table[0].appendChild(tr);
}

function removeExistingRow(event){
    let a = $(event.target);
    let bankDiv = a.parent().parent().find('.bank_div');
    let bik = $.trim(bankDiv.data('bik'));
    let fullName = $.trim(bankDiv.data('fullname'));
    let acc;
    let div = a.parent().parent().find('.editable_div');
    acc = div[0].textContent;
    let deleted = $('#accounts_to_delete').data('value');
    deleted.push(new Account(bik, fullName, $.trim(acc)));
    $('#accounts_to_delete').data('value', deleted);
    a.parent().parent().remove();
}



function removeRow(event){
    let a = $(event.target);
    a.parent().parent().remove();
}

function Account(bik, name, acc){
    this.bik = bik;
    this.name = name;
    this.acc = acc;
}

function SaveDataDTO(accountsToDelete, newAccounts, orgData){
    this.accountsToDelete = accountsToDelete;
    this.newAccounts = newAccounts;
    this.orgData = orgData;
}

function Organization(inn, kpp, fullName, billProlongation, adress, phones){
    this.inn = inn;
    this.kpp = kpp;
    this.fullName = fullName;
    this.billProlongation = billProlongation;
    this.adress = adress;
    this.phone = phones;
}

function save(event){
    let appendEditButtons = $('.append_edit_button');
    let valid = true;
    if  (appendEditButtons.length == 0){
        $('#error_val_row').hide();
        $('#error_row').hide();
    } else {
        appendEditButtons.parent().parent().find('.editable').addClass('error_field');
        $('#error_val_row').show();
        valid = false;
        let editables = $('.editable');
        let hasUnFilledFields = false;
        editables.each(function(index, value){
            if ($(value).val().length == 0 && value.tagName == 'INPUT'){
                hasUnFilledFields = true;
            }
        });

        if (hasUnFilledFields){
            $('#error_row').show();
        } else {
            $('#error_row').hide();
        }
    }

    if (valid){
    submit();
    }
}

function submit(){
    let newAccounts = [];
    let newAccountsRows = $('td .bank_select').parent().parent();
    for(let i = 0; i<newAccountsRows.length; i++){
         let selectedOption = $(':selected', newAccountsRows.eq(i).find('select'));
         let bik = selectedOption.data('bik');
         let fullName = selectedOption.data('fullname');
         let acc = newAccountsRows.eq(i).find('.editable_div')[0].textContent;
         newAccounts.push(new Account($.trim(bik), $.trim(fullName), $.trim(acc)));
    }

    let deletedAccounts = $('#accounts_to_delete').data('value');

    let inn = $('#inn').find('.editable_div')[0].textContent;
    let kpp = $('#kpp').find('.editable_div')[0].textContent;
    let nameOrg = $('#nameOrg').find('.editable_div')[0].textContent;
    let billProlongation = $('#billProlongation').find('.editable_div')[0].textContent;
    let address = $('#address').find('.editable_div')[0].textContent;
    let phones = $('#phones').find('.editable_div')[0].textContent;

    let orgData = new Organization($.trim(inn), $.trim(kpp), $.trim(nameOrg), $.trim(billProlongation), $.trim(address), $.trim(phones));

     $.ajax({
             url: jsRoutes.controllers.inetshop.MainController.saveEdited().url,
             type: "post",
             data: {data:JSON.stringify(new SaveDataDTO(deletedAccounts, newAccounts, orgData))},
             success: function (response) {
                      alert("Данные успешно сохранены!");
                       location.reload();
                     },
                     error: function(jqXHR, textStatus, errorThrown) {
                        console.log(textStatus, errorThrown);
                     }
         });
}

