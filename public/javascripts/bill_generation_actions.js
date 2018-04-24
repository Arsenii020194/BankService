function addRow(event){
$(event.target).remove();
$('#uslug_table_body')
.append($('<tr><td><input type=\"text\"></td><td><input type=\"text\"></td><td><input type=\"text\"></td><td><input type=\"text\"></td><td><input type=\"text\"></td><td><input type=\"text\"></td><td><input type=\"button\" id=\"add_button\" value=\"+\" onclick=\"addRow(event)\"></td></tr>'));
}