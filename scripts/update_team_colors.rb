#!/usr/bin/env ruby

require 'json'

def fetch_colors(season_year)
  json_string = `curl -s http://data.nba.net/prod/#{season_year}/teams_config.json`
  team_configs = JSON.parse(json_string)["teams"]["config"]

  puts "\nTeam Colors\n-----------"

  colors = team_configs.map { |config|
    team_id = config["teamId"].to_i
    abbrev = config["tricode"]
    p_color = config["primaryColor"]
    s_color = config["secondaryColor"]

    if team_id != nil && abbrev != nil && p_color != nil && s_color != nil
      puts "#{abbrev} #{team_id.to_s.rjust(14)} - P: #{p_color} S: #{s_color}"

      # JSON Structure
      {
        id: team_id, 
        abbrev: abbrev, 
        primary_color: p_color, 
        secondary_color: s_color
      }
    else
      nil
    end
  }

  return colors.compact.to_json
end

def fetch_season_year()
  json_string = `curl -s http://data.nba.net/prod/v3/today.json`
  year = JSON.parse(json_string)["seasonScheduleYear"]
  puts "Season Year: #{year}\n"
  return year
end

def write_to_assets_folder(file_name, contents)
  assets_path = File.expand_path("../app/src/main/assets/#{file_name}")
  puts "\nWriting to path: #{assets_path}"
  open(assets_path, 'w') { |file|
    file.puts contents
  }
end

season_year = fetch_season_year()
colors_json = fetch_colors(season_year)
write_to_assets_folder("team_colors.json", colors_json)
