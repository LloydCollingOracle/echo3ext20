function testit() {
    var dataString = "({" + 
		"totalRows: 20, " +
		"rows: [ " +
            "{" +
			    "columns: [ " + 
                    "{" +
				        "value: 'asd'" +
			        "}," +
                    "{" +
                        "value: 'wer'" +
                    "}" +
                "]" +
            "}" +
        "]" +
	"})";
	var data = eval(dataString);
    
    alert(data.totalRows);
    alert(data.rows.length);
    
    for (var i = 0; i < data.rows.length; i++) {
        var row = data.rows[i];
        alert(row.columns.length);
        for (var j = 0; j < row.columns.length; j++) {
            alert(row.columns[j].value);
        }
    }
}
