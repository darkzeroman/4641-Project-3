require '../../Utils.rb'

# puts Dir.entries(Dir.pwd)
list = []

Dir.open(Dir.pwd).each do |file|

	if file.include? ".txt" then
		list << ResultFile.new(file)
	end
end



li = []
list.each {|cluster| li << cluster.percent_incorrect}
p li if li.length > 0


