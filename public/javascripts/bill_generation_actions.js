function addRow(event) {
    let numTd = $('[data-value="num"]').last().children('div').text();
    $('#uslug_table_body').append($('<tr data-status="filling" class="filled_uslugs"><td data-value="num"><div>' + (++numTd) + '</div><td data-value="code"><input type="text" oninput="validateInt(event)"></td><td data-value="type"><input type="text"></td><td data-value="count"><input type="text" oninput="validateInt(event)"></td><td data-value="price"><input type="text" oninput="validateFloat(event)"></td><td data-value="summ"><div></div></td><td><a class="append_edit_button" style="padding: 1px;" onclick="appendEdit(event)"></a></td><td><a class="remove_button" style="padding: 1px;" onclick="removeRow(event)"></a></td>'));
}

function removeRow(event) {
    $(event.target).parent().parent().remove();
    let nums = $('[data-value="num"]');
    for (let i = 0; i < nums.length; i++) {
        nums.eq(i).children().last().text(i + 1);
    }
}

function toDivs(row) {
    let isValid = isRowValid(row);
    let code = row.find('[data-value="code"]').children().eq(0);
    let type = row.find('[data-value="type"]').children().eq(0);
    let count = row.find('[data-value="count"]').children().eq(0);
    let price = row.find('[data-value="price"]').children().eq(0);

    if (isValid) {
        code[0].outerHTML = '<div data-value="code">' + code.val() + '</div>';
        type[0].outerHTML = '<div data-value="type">' + type.val() + '</div>';
        count[0].outerHTML = '<div data-value="count">' + count.val() + '</div>';
        price[0].outerHTML = '<div data-value="price">' + price.val() + '</div>';
        let summ = row.find('[data-value="summ"]').children().eq(0);
        summ.text(Math.round(count.val() * price.val() * 100) / 100);
    }
}

function isRowValid(row) {
    let isValid = true;
    tds = row.children('td');
    for (let i = 0; i < tds.length; i++) {
        let cInput = tds.eq(i).children('input')
        if (cInput) {
            if (cInput.val() == '') {
                cInput.addClass('error_field');
                isValid = false;
            } else {
                cInput.removeClass('error_field');
            }
        }
    }

    if (isValid) {
        $('#error_row').hide();
        row.attr('data-status', 'valid');
    } else {
        $('#error_row').show();
        row.attr('data-status', 'not_valid');
    }
    return isValid;
}

function toInputs(row) {
    let code = row.find('[data-value="code"]').children().eq(0);
    code[0].outerHTML = '<input data-value="code" oninput="validateInt(event)" value="' + code.text() + '"></input>';
    let type = row.find('[data-value="type"]').children().eq(0);
    type[0].outerHTML = '<input data-value="type" value="' + type.text() + '"></input>';
    let count = row.find('[data-value="count"]').children().eq(0);
    count[0].outerHTML = '<input data-value="count" oninput="validateInt(event)" value="' + count.text() + '"></input>';
    let price = row.find('[data-value="price"]').children().eq(0);
    price[0].outerHTML = '<input data-value="price" oninput="validateFloat(event)" value="' + price.text() + '"></input>';
    row.attr('data-status', 'filling');
}

function validateInt(event) {
    event.target.value = (event.target.value.replace(/[^0-9]/g, ''));
    if(event.target.value.length == 10){
        event.target.value = event.target.value.substring(0,9);
    }
}

function validateInt24(event){
    event.target.value = (event.target.value.replace(/[^0-9]/g, ''));
    if(event.target.value.length == 25){
        event.target.value = event.target.value.substring(0,24);
    }
}

function validateFloat(event) {
    event.target.value = event.target.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
}

function appendEdit(event) {
    let row = $(event.target).parent().parent();
    if (isRowValid(row)) {
        toDivs(row);
        $(event.target)[0].outerHTML = '<a style="padding: 1px; cursor:pointer;" class="edit_button" onclick="edit(event)"></a>';
    }
}

function edit(event) {
    let row = $(event.target).parent().parent();
    toInputs(row);
    $(event.target)[0].outerHTML = '<a style="padding: 1px;" class="append_edit_button" onclick="appendEdit(event)"></a>';
}

function generateBill() {
    $('#error_row').hide();
    $('#error_val_row').hide();
    let rows = $('.filled_uslugs');
    let valid = true;
    for (let i = 0; i < rows.length; i++) {
        if (rows.eq(i).attr('data-status') !== 'valid') {
            valid = false;
            if (!isRowValid(rows.eq(i))) {
                $('#error_row').show();
            }
            $('#error_val_row').show();
        }
    }

    let checkedInputs = $('.checked_input');
    for (let i = 0; i < checkedInputs.length; i++) {
        if (checkedInputs.eq(i).val() == '') {
            checkedInputs.eq(i).addClass('error_field');
            $('#error_row').show();
            valid = false;
        } else {
            checkedInputs.eq(i).removeClass('error_field');
        }
    }

    if (valid) {
        let uslugs = $('.filled_uslugs');
        let uslArr = new Array();
        for (let i = 0; i < uslugs.length; i++) {
            let num = uslugs.eq(i).find('[data-value="num"]').children().eq(0).text();
            let code = uslugs.eq(i).find('[data-value="code"]').children().eq(0).text();
            let type = uslugs.eq(i).find('[data-value="type"]').children().eq(0).text();
            let count = uslugs.eq(i).find('[data-value="count"]').children().eq(0).text();
            let price = uslugs.eq(i).find('[data-value="price"]').children().eq(0).text();
            let summ = uslugs.eq(i).find('[data-value="summ"]').children().eq(0).text();
            uslArr.push(new Uslug(num, code, type, count, price, summ));
        }
        let numOrder = $('#order_number').val();
        let billNumber = $('#bill_number').val();
        let customer = $('#customer').val();
        let finalSum = getFinalSum();
        let bill = new Bill(numOrder, billNumber, customer, uslArr, finalSum, 'Оплата по заказу №' + numOrder);
        downloadPdf(JSON.stringify(bill));
    }
}

function getFinalSum(){
    let summ = 0;
    $('[data-value="summ"]').each(function(index, value){
        summ = summ + $(value.children[0]).text();
    });

    return Math.round(summ * 100) / 100;
}

function Uslug(num, code, type, count, price, summ) {
    this.num = num;
    this.code = code;
    this.type = type;
    this.count = count;
    this.price = price;
    this.summ = summ;
}


function Bill(numOrder, billNumber, customer, uslugs, finalSum, billDest) {
    this.numOrder = numOrder;
    this.billNumber = billNumber;
    this.customer = customer;
    this.uslugs = uslugs;
    this.finalSum = finalSum;
    this.billDest = billDest;
}

function downloadPdf(json){
    var xhr = new XMLHttpRequest();

    xhr.open("POST", jsRoutes.controllers.BillGenerationController.generatePdf().url, true)
    xhr.setRequestHeader('Content-type', 'text/plain; charset=utf-8');
    xhr.onload = function (event) {
             var blob = xhr.response;
             var fileName = xhr.getResponseHeader("fileName") //if you have the fileName header available
             var link=document.getElementById('hidden_link');
             link.href=window.URL.createObjectURL(blob);
             link.download=fileName + '.pdf';
             link.click();
         };
    xhr.responseType = 'blob';
    xhr.send(json);
}

function downloadBillFile(event){
var xhr = new XMLHttpRequest();

    xhr.open("GET", jsRoutes.controllers.BillGenerationController.download($(event.target).data('doc')).url, true)
    xhr.setRequestHeader('Content-type', 'text/plain; charset=utf-8');
    xhr.onload = function (event) {
             var blob = xhr.response;
             var fileName = 'download' //if you have the fileName header available
             var link=document.getElementById('hidden_link');
             link.href=window.URL.createObjectURL(blob);
             link.download=fileName + '.pdf';
             link.click();
         };
    xhr.responseType = 'blob';
    xhr.send();
}