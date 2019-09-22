#!/usr/bin/env ruby

def get_package_name()
  pn = "io.github.vishnumad.nbascores"
  puts "Package Name: #{pn}"
  return pn
end

package_name = get_package_name()
system("adb shell pm clear #{package_name}")
