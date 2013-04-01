require '../../../Utils.rb'
require 'descriptive_statistics'

# puts Dir.pwd

md_list = []

Dir.open(Dir.pwd).each do |file|
	if file.include? ".txt"
		md_list << ResultFile.new(file)
	end
end

li = []
md_list.each { |cluster| li << cluster.percent_incorrect}
p li.join(',')
# p [li.mean, li.standard_deviation, li.variance].inspect.gsub("\[",'').gsub("\]",'')

# p li.min
