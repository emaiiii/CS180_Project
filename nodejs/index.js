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

        fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\account.csv','utf8',function (err,data) {
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
            var indices = hashmap['username'][qdata.username.toLowerCase()];
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

        fs.readFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\account.csv','utf8',function (err,data) {
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
              var input = qdata.username + ',' + qdata.password + ',' + qdata.firstname + ',' + qdata.lastname + ',' + qdata.email + '\r\n';
              console.log(input);
              fs.appendFile('C:\\Users\\jim19\\Desktop\\cs180\\database\\account.csv', input, 'utf8', function(err) {
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
                console.time("player avg time");

                /*
                var fgm = avg_data(data,4,5,9,playerID).toFixed(2);
                var fga = avg_data(data,4,5,10,playerID).toFixed(2);
                var fg_pct = avg_data(data,4,5,11,playerID).toFixed(3);
                var fg3m = avg_data(data,4,5,12,playerID).toFixed(2);
                var fg3a = avg_data(data,4,5,13,playerID).toFixed(2);
                var fg3_pct = avg_data(data,4,5,14,playerID).toFixed(3);
                var ftm = avg_data(data,4,5,15,playerID).toFixed(2);
                var fta = avg_data(data,4,5,16,playerID).toFixed(2);
                var ft_pct = avg_data(data,4,5,17,playerID).toFixed(3);
                var oreb = avg_data(data,4,5,18,playerID).toFixed(2);
                var dreb = avg_data(data,4,5,19,playerID).toFixed(2);
                var reb = avg_data(data,4,5,20,playerID).toFixed(2);
                var ast = avg_data(data,4,5,21,playerID).toFixed(2);
                var stl = avg_data(data,4,5,22,playerID).toFixed(2);
                var blk = avg_data(data,4,5,23,playerID).toFixed(2);
                var to = avg_data(data,4,5,24,playerID).toFixed(2);
                var pf = avg_data(data,4,5,25,playerID).toFixed(2);
                var pts = avg_data(data,4,5,26,playerID).toFixed(2);
                var all_stats = fgm + "," + fga + "," + fg_pct + "," + fg3m + "," + fg3a + "," + fg3_pct + "," + ftm + "," + fta + "," + ft_pct + "," + oreb + "," + dreb + "," + reb + "," + ast + "," + stl + "," + blk + "," + to + "," + pf + "," + pts;
                console.log(all_stats);
                send_payload(res,all_stats);
                */
                var avg = avg_data(data,9,26,playerID,4,2) 
                console.log(avg);
                send_payload(res,  JSON.stringify(avg));

                console.timeEnd("player avg time");
                
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
                  console.time("team avg time");

                  var avg = avg_data(data,7,12,teamID,3,4);
                  avg.splice(6,1);
                  console.log(avg);
                  send_payload(res,  JSON.stringify(avg));

                  console.timeEnd ("team avg time");
                }
              });
            }
          }
        });
      }
    }

    //send userset player data
    else if(qdata.userset != undefined) {
      var path = 'C:\\Users\\jim19\\Desktop\\cs180\\database\\usersets\\' + qdata.userset + '.csv';
      console.log('sending userset info');
      console.log(qdata.userset);
      

      fs.readFile(path,'utf8',function (err,csv_data) {
        //cannot open file
        if (err) {
          console.error(err);
          send_payload(res,'empty userset');
        }
        //file opened
        else {
          var data = send_data(csv_data);
          send_payload(res,JSON.stringify(data));
        }
      });
    }

    //adding player to userset
    else if(qdata.addplayer != undefined) {
      console.log('----------------------');
      console.log('adding player to userset')
      console.log(qdata.addplayer);
      var path = 'C:\\Users\\jim19\\Desktop\\cs180\\database\\usersets\\' + qdata.addusername + '.csv';
      fs.readFile(path,'utf8',function (err,csv_data) {
        //cannot open file, create a new file
        if (err) {
          console.log('creating a file');
          fs.appendFile(path, 'playername \r\n', 'utf8', function(err) {
            if(err) return console.log(err);
          });
          fs.appendFile(path, qdata.addplayer + '\r\n', 'utf8', function(err) {
            if(err) return console.log(err);
          });
          send_payload(res,'player added to userset');
        }
        //file opened
        else {
          //check if player exists in set already
          var userSetData = csv_data.split(/\r?\n/);
          var found = 0;

          userSetData.forEach((element) => {
            if(element.toLowerCase() == qdata.addplayer.toLowerCase()) {
              console.log('player already exists in set');
              send_payload(res,'player already exists in set');
              found = 1;
            }
          })
          
          if(found == 0) {
            fs.appendFile(path, qdata.addplayer + '\r\n', 'utf8', function(err) {
              if(err) return console.log(err);
            });
            send_payload(res,'player added to userset');
          }
          
          

        }
      });
    }

    //clearing userset
    else if(qdata.clearplayer == 1) {
      console.log('----------------------');
      console.log('clear player userset')
      console.log(qdata.clearusername);
      var path = 'C:\\Users\\jim19\\Desktop\\cs180\\database\\usersets\\' + qdata.clearusername + '.csv';

      fs.unlink(path,(err) => {
        if(err) {
          console.log('already cleared');
        }
        else {
          console.log('cleared');
        }
      })

    }

    //deleting from userset
    else if(qdata.delplayer != undefined) {
      console.log('---------------------------');
      console.log('delete player from userset')
      console.log(qdata.delusername);
      console.log(qdata.delplayer);
      var path = 'C:\\Users\\jim19\\Desktop\\cs180\\database\\usersets\\' + qdata.delusername + '.csv';
      fs.readFile(path,'utf8',function (err,csv_data) {
        //cannot open file
        if (err) {
          console.error(err);
        }
        //file opened
        else {
          var userData = csv_data.split(/\r?\n/);
          var idx = -1;
          var count = 0;

          userData.forEach(element => {
            if(element.toLowerCase() == qdata.delplayer.toLowerCase()) {
              idx = count; 
            }
            count++;
          });

          if(idx == -1) {
            send_payload(res,'no player found');
            console.log('no player found');
          }
          else{
            userData.splice(idx,1);
            var input = '';
            userData.forEach(element => {
              if(element != '') {
                input += element + '\r\n';
              }
            });
            
            if(userData[1] == '') {
              fs.unlink(path,(err) => {
                if(err) {
                  console.log('already cleared');
                }
                else {
                  console.log('cleared');
                }
              })
            }
            else {
              fs.writeFile(path,input, function(err,data) {
                if(err) {
                  return console.err(err);
                }
              })
            }
            console.log('player deleted');
            send_payload(res,'player deleted');
          }
        }
      });
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

function send_data(data) {
  var userdata = data.split(/\r?\n/);

  //if userset is empty
  if(userdata[0] == '') {
    console.log('empty userset');
    return ('empty userset');
  }
  
  else {
    userdata.shift();
    if(userdata.slice(-1).pop() == '') {
      userdata.pop();
    }
    console.log(userdata);
    return (userdata);
  }

}


function avg_data(csv_data,start_idx,end_idx,search,check_idx, second_check_idx) {
  var avg = 0;
  var count = 0;

  var avgArr = new Array (end_idx - start_idx + 1);
  avgArr.fill(0);

  var stats = csv_data.split(/\r?\n/);

  stats.forEach(function (row) {
    var elements = row.split(",");

    if (elements[check_idx] == search) {
      var arr_count = 0;
      for(var i = start_idx; i <= end_idx; i++) {
        if(!isNaN(elements[i]) && elements[i] != '') {
          avgArr[arr_count] += Number(elements[i]);
          
          arr_count++;
        }
      }
      count++;
    }

    else if (elements[second_check_idx] == search) {
      var arr_count = 0;
      for(var i = start_idx+7; i <= end_idx+7; i++) {
        if(!isNaN(elements[i]) && elements[i] != '') {
          avgArr[arr_count] += Number(elements[i]);
          
          arr_count++;
        }
      }
      count++;
    }

  });
  
  for(var i = 0; i < avgArr.length; i++) {
    avgArr[i] /= count;
    avgArr[i] = avgArr[i].toFixed(2);
  }
  
  return avgArr;
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
      /* 
      Get rid of weird invisible characters that don't belong to UTF-8 (eg. accept only 0x20 - 0x7F). See UTF-8 table: http://www.asciitable.com/.
      Try out regex here: https://regex101.com/ 
      */
      col = col.replace(/[^\x20-\x7F]+/g, '');
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