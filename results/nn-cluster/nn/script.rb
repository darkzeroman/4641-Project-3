require '../../../Utils.rb'

list = []

Dir.open(Dir.pwd).each do |file|
	if file.include? ".txt"
		list << ResultFile.new(file)
	end
end

li = []
list.each { |cluster| li << cluster.percent_incorrect}
p li.join(',')
# p [li.mean, li.standard_deviation, li.variance].inspect.gsub("\[",'').gsub("\]",'')