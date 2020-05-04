const http = require('http');
const url = require('url');
const fs = require('fs');

const hostname = '127.0.0.1';
const port = 3000;
var team1ID = 0;
var team2ID = 0;

const server = http.createServer(function (req,res) {
  if(req.url === '/') {
    res.writeHead(200, {'Content-type' : 'text/plain'});
    res.end('Home page\n');
  }
  
  else if(req.url != '/favicon.ico') {
    var q = url.parse(req.url,true);

    var qdata = q.query;

    //if team is being searched
    if(qdata.team != undefined) {
      console.log('------------------------');
      console.log('searching for team');
      console.log(qdata.team);
      //reading file for teams.csv
      fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\teams.csv','utf8',function (err,data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          var ret = process_data(data);
          var table = ret.table;
          var hashmap = ret.hashmap;
          var indices = hashmap['nickname'][qdata.team.toLowerCase()];
          
          if(indices == undefined) {
            console.log('no team found');
            send_payload(res,'no team info found');
          }
          else {
            var payload = [];
            indices.forEach(function(idx) {
              payload.push(table[idx]);
            });
            if (payload.length > 0) {
              send_payload(res, JSON.stringify(payload[0]));
            } else {
              console.log('no team info found');
              send_payload(res, 'no team info found');
            }
        }
          
        }
      });
      console.log('------------------------');
    }

    //if player is being searched
    else if(qdata.player != undefined) {
      console.log('------------------------');
      console.log('searching for player');
      console.log(qdata.player);


      fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\players.csv','utf8',function (err,data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          //split up the rows from csv file
          var playerData = data.split(/\r?\n/);
          var playerFound = 0;

          playerData.forEach(function (row) {
            var elements = row.split(",");
            var nickname = elements[0];
            
            if (nickname !== undefined) {
              if ( (nickname.toLowerCase() == qdata.player.toLowerCase()) && (playerFound == 0)) {
                playerFound = 1;
                console.log('returned player info');
                send_payload(res, JSON.stringify(elements));
                return;
              }
            }
          })
          //if no player were found
          if(playerFound == 0) {
            console.log('no player info found');
            send_payload(res, 'no player info found');
          }
        }
      });
      console.log('------------------------');
    }

    //searching games between two teams
    else if(qdata.team1 != undefined && qdata.team2 != undefined) {
      console.log('------------------------');
      console.log('searching for games');
      

      //look for team 1 & team 2 ID
      fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\teams.csv','utf8',function (err,data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          //split up the rows from csv file
          var team1Data = data.split(/\r?\n/);
          var team1Found = 0; 
          
          var team2Found = 0;

          team1Data.forEach(function (row) {
            var elements = row.split(",");
            var nickname = elements[5];
            if (nickname !== undefined) {
              if (nickname.toLowerCase() == qdata.team1.toLowerCase()) {
                team1Found = 1;
                team1ID = elements[1];
                console.log(team1ID);
              }
              if (nickname.toLowerCase() == qdata.team2.toLowerCase()) {
                team2Found = 1;
                team2ID = elements[1];
                console.log(team2ID);
              }
            }
          })
          //if team not found
          if(team1Found == 0 && team2Found == 0) {
            console.log('team 1 and 2 info found');
            console.log('------------------------');
            send_payload(res, 'team 1 and 2 not found');
          }
          else if(team1Found == 0) {
            console.log('team 1 info found');
            console.log('------------------------');
            send_payload(res, 'team 1 info found');
          }
          else if (team2Found == 0) {
            console.log('team 2 info found');
            console.log('------------------------');
            send_payload(res, 'team 2 info found');
          }
          else {
            fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\games.csv','utf8',function (err,data) {
              //cannot open file
              if (err) {
                console.error(err);
              }
              else {
                var ret = process_data(data);
                var table = ret.table;
                var hashmap = ret.hashmap;
                

                var s_indices = hashmap['season'][qdata.season];
                var h_indices = hashmap['home_team_id'][team1ID];
                var v_indices = hashmap['visitor_team_id'][team2ID];

                //if year is invalid
                if(s_indices == undefined) {
                  console.log('invalid year');
                  send_payload(res,'invalid year')
                } 
                else {
                  var final_indices = s_indices.filter(x=>h_indices.includes(x));
                  final_indices = final_indices.filter(x => v_indices.includes(x));

                  var payload = [];

                  final_indices.forEach(function(idx) {
                    payload.push(table[idx]);
                  });
                  if (payload.length > 0) {
                    send_payload(res, JSON.stringify(payload));
                  } 
                  else {
                    console.log('no game info found');
                    send_payload(res, 'no game info found');
                  }
                }
              }
            });
          }
        }
      });
    }

    //searching for certain game's details
    else if(qdata.gamedeid != undefined) {
      fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\games_details.csv','utf8',function (err,data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          var ret = process_data(data);
          var table = ret.table;
          var hashmap = ret.hashmap;
          var indices = hashmap['game_id'][qdata.gamedeid.toLowerCase()];

          var payload = [];

          if(indices == undefined) {
            console.log('invalid game id');
            send_payload(res,'invalid game id')
          } 
          else {
            indices.forEach(function(idx) {
              payload.push(table[idx]);
            });
            if (payload.length > 0) {
              send_payload(res, JSON.stringify(payload));
            } else {
              console.log('no game info found');
              send_payload(res, 'no game info found');
            }
        }
        }
      });
    }

    //searching for game with gameid
    else if(qdata.gameid != undefined) {
      console.log('searching for game with gameid');
      fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\games.csv','utf8',function (err,data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          console.log(qdata.gameid);
          var ret = process_data(data);
          var table = ret.table;
          var hashmap = ret.hashmap;
          var indices = hashmap['game_id'][qdata.gameid.toLowerCase()];

          var payload = [];

          if(indices == undefined) {
            console.log('invalid game id');
            send_payload(res,'invalid game id')
          } 
          else {
            indices.forEach(function(idx) {
              payload.push(table[idx]);
            });
            if (payload.length > 0) {
              send_payload(res, JSON.stringify(payload[0]));
            } else {
              console.log('no game info found');
              send_payload(res, 'no game info found');
            }
        }
        }
      });
    }

    //user login section
    else if((qdata.username != undefined || qdata.password != undefined) && qdata.register == undefined) {
      //if username is empty
      if(qdata.username == undefined) {
        send_payload(res,'Empty Username');
        console.log('empty username');
      }
      //if password is empty
      else if(qdata.password == undefined) {
        send_payload(res, 'Empty Password');
        console.log('empty Password');
      }
      else{
        console.log('---------------------');
        console.log('User Login');

        fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\users.csv','utf8',function (err,data) {
          //cannot open file
          if (err) {
            console.error(err);
          }
          //file opened
          else {
            //looking up username
            console.log(qdata.username);
            var ret = process_data(data);
            var hashmap = ret.hashmap;
            var indices = hashmap['username'][qdata.username];
            //no username found
            if(indices == undefined) {
              console.log('Incorrect Username');
              send_payload(res,'Incorrect Username or Password');
            } 
            //username found
            else {
              var userIdx = indices[0];
              console.log(qdata.password);
              var ret = process_data(data);
              var table = ret.table;
              var hashmap = ret.hashmap;
              var indices = hashmap['password'][qdata.password];
              if(userIdx == indices) {
                console.log('login success');
                send_payload(res,'Welcome Back');
              }
              else {
                console.log('incorrect password');
                send_payload(res,'Incorrect Username or Password');
              }
            }
          }
        });
        
      }

    }

    //user register
    else if(qdata.register == 1) {

        fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\users.csv','utf8',function (err,data) {
          //cannot open file
          if (err) {
            console.error(err);
          }
          //file opened
          else {
            //looking up username
            console.log(qdata.username);
            var ret = process_data(data);
            var hashmap = ret.hashmap;
            var indices = hashmap['username'][qdata.username];
            //username used
            if(indices != undefined) {
              console.log('username used');
              send_payload(res,'Username used');
            }
            //username not used
            else{
              var input = qdata.username + ',' + qdata.password + ',' + qdata.firstname + ',' + qdata.lastname + ',' + qdata.email + '\n';
              console.log(input);
              fs.appendFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\users.csv', input, 'utf8', function(err) {
                if(err) return console.log(err);
              });
              send_payload(res,'Register Successful');
            } 
          }
        });
    }

    //player analysis
    else if(qdata.playeravg == 1) {
      console.log('---------------------------')
      console.log('looking for player avg')
      if(qdata.name == undefined) {
        console.log('Empty player name');
        send_payload(res,'Empty player name');
      }
      else {
        console.log(qdata.name);
        fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\players.csv','utf8',function (err,data) {
          //cannot open file
          if (err) {
            console.error(err);
          }
          //file opened
          else {
            var playerData = data.split(/\r?\n/);
            var playerFound = 0;
            var playerID = 0;

            playerData.forEach(function (row) {
              var elements = row.split(",");
              var nickname = elements[0];
              
              if (nickname !== undefined) {
                if ( (nickname.toLowerCase() == qdata.name.toLowerCase()) && (playerFound == 0)) {
                  playerFound = 1;
                  playerID = elements[2];
                  console.log(elements[2]);
                }
              }
            })
            //if no player were found
            if(playerFound == 0) {
              console.log('no player info found');
              send_payload(res, 'no player info found');
            }
            else {
              fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\games_details.csv','utf8',function (err,data) {
                if (err) {
                  console.error(err);
                }
                else {
                var fgm = avg_data(data,4, 9,playerID).toFixed(2);
                var fga = avg_data(data,4,10,playerID).toFixed(2);
                var fg_pct = avg_data(data,4,11,playerID).toFixed(3);
                var fg3m = avg_data(data,4,12,playerID).toFixed(2);
                var fg3a = avg_data(data,4,13,playerID).toFixed(2);
                var fg3_pct = avg_data(data,4,14,playerID).toFixed(3);
                var ftm = avg_data(data,4,15,playerID).toFixed(2);
                var fta = avg_data(data,4,16,playerID).toFixed(2);
                var ft_pct = avg_data(data,4,17,playerID).toFixed(3);
                var oreb = avg_data(data,4,18,playerID).toFixed(2);
                var dreb = avg_data(data,4,19,playerID).toFixed(2);
                var reb = avg_data(data,4,20,playerID).toFixed(2);
                var ast = avg_data(data,4,21,playerID).toFixed(2);
                var stl = avg_data(data,4,22,playerID).toFixed(2);
                var blk = avg_data(data,4,23,playerID).toFixed(2);
                var to = avg_data(data,4,24,playerID).toFixed(2);
                var pf = avg_data(data,4,25,playerID).toFixed(2);
                var pts = avg_data(data,4,26,playerID).toFixed(2);
                var all_stats = fgm + "," + fga + "," + fg_pct + "," + fg3m + "," + fg3a + "," + fg3_pct + "," + ftm + "," + fta + "," + ft_pct + "," + oreb + "," + dreb + "," + reb + "," + ast + "," + stl + "," + blk + "," + to + "," + pf + "," + pts;
                console.log(all_stats);
                send_payload(res,all_stats);
                
                }
                
              });
            }
          }

        });
      }


    }

    //team analysis
    else if(qdata.teamavg == 1) {
      console.log('---------------------------');
      console.log('looking for team avg');
      if(qdata.name == undefined) {
        console.log('Empty team name');
        send_payload(res,'Empty team name');
      }
      else {
        console.log(qdata.name);
        fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\teams.csv','utf8',function (err,data) {
          //cannot open file
          if (err) {
            console.error(err);
          }
          //file opened
          else {
            var teamData = data.split(/\r?\n/);
            var teamFound = 0;
            var teamID = 0;

            teamData.forEach(function (row) {
              var elements = row.split(",");
              var nickname = elements[5];
              
              if (nickname !== undefined) {
                if ( (nickname.toLowerCase() == qdata.name.toLowerCase()) && (teamFound == 0)) {
                  teamFound = 1;
                  teamID = elements[1];
                  console.log(elements[1]);
                }
              }
            })
            //if no player were found
            if(teamFound == 0) {
              console.log('no team info found');
              send_payload(res, 'no team info found');
            }
            else {
              fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\games.csv','utf8',function (err,data) {
                if (err) {
                  console.error(err);
                }
                else{
                  var pts = avg_data(data,3, 4, 7,teamID).toFixed(2);
                  var fg_pct = avg_data(data,3, 4, 8,teamID).toFixed(3);
                  var ft_pct = avg_data(data,3, 4, 9,teamID).toFixed(3);
                  var fg3_pct = avg_data(data,3, 4, 10,teamID).toFixed(3);
                  var ats = avg_data(data,3, 4, 11,teamID).toFixed(2);
                  var reb = avg_data(data,3, 4, 12,teamID).toFixed(2);
                  var stats = pts + "," + fg_pct + "," + ft_pct + "," + fg3_pct + "," + ats + "," + reb;
                  console.log(stats);
                  send_payload(res,stats);
                }



              });
            }
          }
        });
      }
    }

    else {
      console.log('------------------------');
      console.log('invalid');
      console.log('------------------------');
      send_payload(res, 'invalid');
    }

  }
  
});

function send_payload(res, msg) {
  res.writeHead(200, {'Content-type': 'application/json'});
  res.write(msg);
  res.end();
}

function avg_data(csv_data,check_idx,second_idx,idx,search) {
  var avg = 0;
  var count = 0;

  var stats = csv_data.split(/\r?\n/);

  stats.forEach(function (row) {
    var elements = row.split(",");
      
    if (elements[check_idx] == search) {
      if(!isNaN(elements[idx]) && elements[idx] != '') {
        avg += Number(elements[idx]);
        count += 1;
      }
    }
    if(elements[second_idx] == search) {
      if(!isNaN(elements[idx+7]) && elements[idx+7] != '') {
        avg += Number(elements[idx+7]);
        count += 1;
      }
    }
  });
  avg = avg / count;
  
  return avg;
}

function process_data(csv_data) {
  var rows = csv_data.split(/\r?\n/);
  var hashmap = {};
  var table = [];
  var headers = [];

  rows.forEach(function (row, row_idx) {
    var cols = row.split(",");
    table.push(cols);
    cols.forEach(function(col, col_idx){
      col = col.toLowerCase();
      if (row_idx === 0) {
        hashmap[col] = {};
        headers.push(col);
      }
      else {
        if(hashmap[headers[col_idx]] == undefined) {
          
        }
        else if (col in hashmap[headers[col_idx]]) {
          hashmap[headers[col_idx]][col].push(row_idx);
        } else {
          hashmap[headers[col_idx]][col] = [row_idx];
        }
        
      }
    });
  })
  return {
    table: table, hashmap: hashmap, headers:headers}
}

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});