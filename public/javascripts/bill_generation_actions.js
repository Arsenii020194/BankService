function addRow(event){
    let numTd = $('[data-value="num"]').last().children('div').text();
    $('#uslug_table_body').append($('<tr data-status="filling" class="filled_uslugs"><td data-value="num"><div>' + (++numTd) + '</div><td data-value="code"><input type="text" oninput="validateInt(event)"></td><td data-value="type"><input type="text"></td><td data-value="count"><input type="text" oninput="validateInt(event)"></td><td data-value="price"><input type="text" oninput="validateFloat(event)"></td><td data-value="summ"><div></div></td><td><a class="append_edit_button" style="padding: 1px;" onclick="appendEdit(event)"></a></td><td><a class="remove_button" style="padding: 1px;" onclick="removeRow(event)"></a></td>'));
}

function Bill(){

}

function removeRow(event){
    $(event.target).parent().parent().remove();
    let nums = $('[data-value="num"]');
    for(let i=0; i< nums.length; i++){
      nums.eq(i).children().last().text(i+1);
    }
}

function toDivs(row){
    let isValid = isRowValid(row);
    let codeTd = row.find('[data-value="code"]').children().eq(0);
    let typeTd = row.find('[data-value="type"]').children().eq(0);
    let countTd = row.find('[data-value="count"]').children().eq(0);
    let priceTd = row.find('[data-value="price"]').children().eq(0);

    if(isValid){
        codeTd[0].outerHTML = '<div data-value="code">' + codeTd.val() + '</div>';
        typeTd[0].outerHTML = '<div data-value="type">' + typeTd.val() + '</div>';
        countTd[0].outerHTML = '<div data-value="count">' + countTd.val() + '</div>';
        priceTd[0].outerHTML = '<div data-value="price">' + priceTd.val() + '</div>';
        let summTd = row.find('[data-value="summ"]').children().eq(0);
        summTd.text(countTd.val() * priceTd.val());
    }
}

function isRowValid(row){
    let isValid = true;
    tds = row.children('td');
    for(let i=0; i<tds.length; i++){
        let cInput = tds.eq(i).children('input')
        if(cInput){
            if (cInput.val() == ''){
                    cInput.addClass('error_field');
                    isValid = false;
                } else {
                    cInput.removeClass('error_field');
                }
        }
    }

    if(isValid){
        $('#error_row').hide();
        row.attr('data-status', 'valid');
    } else {
        $('#error_row').show();
        row.attr('data-status', 'not_valid');
    }
    return isValid;
}

function toInputs(row){
    codeTd = row.find('[data-value="code"]').children().eq(0);
    codeTd[0].outerHTML = '<input data-value="code" oninput="validateInt(event)" value="'+ codeTd.text() + '"></input>';
    let typeTd = row.find('[data-value="type"]').children().eq(0);
    typeTd[0].outerHTML = '<input data-value="type" value="'+ typeTd.text() + '"></input>';
    let countTd = row.find('[data-value="count"]').children().eq(0);
    countTd[0].outerHTML = '<input data-value="count" oninput="validateInt(event)" value="'+ countTd.text() + '"></input>';
    let priceTd = row.find('[data-value="price"]').children().eq(0);
    priceTd[0].outerHTML = '<input data-value="price" oninput="validateFloat(event)" value="'+ priceTd.text() + '"></input>';
    row.attr('data-status', 'filling');
}

function validateInt(event){
     event.target.value = (event.target.value.replace(/[^0-9]/g, ''));
}

function validateFloat(event){
    event.target.value = event.target.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
}

function appendEdit(event){
    let row =  $(event.target).parent().parent();
    if(isRowValid(row)){
        toDivs(row);
        $(event.target)[0].outerHTML = '<a style="padding: 1px; cursor:pointer;" class="edit_button" onclick="edit(event)"></a>';
    }
}

function Uslug(num, code, type, count, price, summ){
    this.num = num;
    this.code = code;
    this.type = type;
    this.count = count;
    this.price = price;
    this.summ = summ;
}

function edit(event){
 let row = $(event.target).parent().parent();
 toInputs(row);
 $(event.target)[0].outerHTML = '<a style="padding: 1px;" class="append_edit_button" onclick="appendEdit(event)"></a>';
}

function generateBill(){
    $('#error_row').hide();
    $('#error_val_row').hide();
    let rows = $('.filled_uslugs');
    let valid = true;
    for(let i=0; i<rows.length; i++){
        if(rows.eq(i).attr('data-status')!=='valid'){
            valid = false;
            if(!isRowValid(rows.eq(i))){
                $('#error_row').show();
            }
            $('#error_val_row').show();
        }
    }

    let checkedInputs = $('.checked_input');
    for(let i=0; i< checkedInputs.length; i++){
        if(checkedInputs.eq(i).val()==''){
                checkedInputs.eq(i).addClass('error_field');
                $('#error_row').show();
                valid = false;
            }else{
                checkedInputs.eq(i).removeClass('error_field');
            }
    }

    if (valid){

    }
}