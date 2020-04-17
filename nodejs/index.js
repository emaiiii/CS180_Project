const http = require('http');
const url = require('url');
const fs = require('fs');

const hostname = '127.0.0.1';
const port = 3000;

paths = {'teams': 'C:\\Users\\jim19\\Desktop\\cs180\\database\\teams.csv',
        'players': 'C:\\Users\\jim19\\Desktop\\cs180\\database\\players.csv'}

const server = http.createServer(function (req,res) {
  if(req.url === '/') {
    res.writeHead(200, {'Content-type' : 'text/plain'});
    res.end('Hello World\n');
  }
  
  else if(req.url != '/favicon.ico') {
    var q = url.parse(req.url,true);

    var qdata = q.query;
    console.log(qdata.nameOfFile);

    //if team is being searched
    if(qdata.team != 0) {
      //reading file for teams.csv
      fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\teams.csv','utf8',function (err,data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          //split up the rows from csv file
          var teamData = data.split(/\r?\n/);
          var teamDetails = "";
          var column;
          
          teamData.forEach(function (row) {
            var elements = row.split(",");
            var nickname = elements[5];
            if (nickname !== undefined) {
              if (nickname.toLowerCase() == qdata.team.toLowerCase()) {
                console.log('check');
                res.writeHead(200, {'Content-type': 'application/json'});
                res.write(JSON.stringify(elements));
                res.end();
                return;
              }
            }
          })
        }
      });

    }

    console.log('recieved request');
  }
  
});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});



/*
teamData.forEach(function (row, idx) {
  if (idx == 0) {
    var column_names = row.split(",");
    var elements_by_columns = {};
    column_names.forEach(function (name) {
      elements_by_columns[name] = [];
    });
  }
  else {
    var elements = row.split(",");
    elements.forEach(function(element, idx) {
      elements_by_columns[column_names[idx]].push(element)})
  }
*/